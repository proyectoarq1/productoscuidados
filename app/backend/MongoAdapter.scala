package backend
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObjectBuilder
import play.api.Logger



class MongoAdapter(mongo_uri: String) {

  var uri: String = mongo_uri
  
  val client_uri = MongoClientURI(uri)
  val mongoClient = MongoClient(client_uri)
  
  var mongoDB = mongoClient.getDB(client_uri.database.get)
  
  var builder = MongoDBObject.newBuilder
  
  override def toString(): String = "Mongo adapter conected to " + client_uri.hosts(0) + "/" + client_uri.database.get;

  
  def insert_document(colection_name : String, document : MongoDBObject) = mongoDB(colection_name).insert(document);  
  def drop_collection(colection_name : String) = mongoDB(colection_name).drop();

  def toProductDetail(produc_detail: MongoDBObject) : Option[ProductDetail] = {

      val type_product_exist = produc_detail.getAs[String]("type_product")
      if (!type_product_exist.isEmpty){
      var type_of_capability : Option[String] = None
      var type_of_container : Option[String] = None
      val type_product = type_product_exist.get
      val brand = produc_detail.getAs[String]("brand").get
      val amount =  produc_detail.getAs[Double]("amount").get
      
      val type_capability = produc_detail.getAs[Option[String]]("type_of_capability")    
      if (! type_capability.isEmpty) 
      { type_of_capability = type_capability.get}
      
      val type_container = produc_detail.getAs[Option[String]]("type_of_container")    
      if (! type_container.isEmpty) 
      { type_of_container = type_container.get} 

      val product = new ProductDetail(brand,type_product,amount,type_of_capability,type_of_container)
      
   
    return Some(product) }
      return None
  }
  
 
  
  def toFoundPrice(found_price: MongoDBObject) : FoundPrice = {

    val product_id = found_price.getAs[String]("product_id").get
    val price =  found_price.getAs[Double]("price").get
    val datetime =  found_price.getAs[String]("datetime").get
    val shop_id =  found_price.getAs[String]("shop_id").get
    
    var product_detail : Option[ProductDetail] = None
    val product = found_price.getAs[MongoDBObject]("product_detail")
    if (!(product.isEmpty))
      product_detail = toProductDetail(product.get);
    
    return new FoundPrice(product_id, price, datetime, shop_id, product_detail)
    
  }
  
  def toShop(shop: MongoDBObject) : Shop = {
    
    val latitude = shop.getAs[Double]("latitude").get
    val longitude = shop.getAs[Double]("longitude").get
    val address = shop.getAs[String]("address").get
    val location = shop.getAs[String]("location").get
    val name = shop.getAs[String]("name").get
    
    val new_shop = new Shop(latitude, longitude, address, location, name)
    new_shop.set_uuid(shop.get("_id").get.toString())
    return new_shop
    
    
    
  }
 

  def toMongoDBObjetc(o : Object) : MongoDBObject = o match {
    case o:Shop => MongoDBObject("latitude" -> o.latitude, 
                                    "longitude" -> o.longitude, 
                                    "address" -> o.address,
                                    "location" -> o.location,
                                    "name" -> o.name)
    case o:FoundPrice => MongoDBObject("product_id" -> o.product_id,
                                             "price" -> o.price,
                                             "shop_id" -> o.shop_id,
                                             "datetime" -> o.datetime,
                                             "product_detail" -> toMongoDBObjetc(o.product_detail))
    
    case Some(product : ProductDetail) => MongoDBObject("brand" -> product.brand,
                                          "type_product" -> product.type_product,
                                          "amount" -> product.amount,
                                          "type_of_capability" -> product.type_of_capability,
                                          "type_of_container" -> product.type_of_container)
    case None => MongoDBObject()

 }
  
  def delete(o : Object) : Boolean = {
    
    val result = o match {
      case o: Shop => delete_shop(o);
      case o:FoundPrice => delete_found_price(o)
    }
   val deleted = result match {
      case Some(l) => Logger.info("[MONGO ADAPTER] Deleted was succesfull"); true
      case None => Logger.info("[MONGO ADAPTER] There was no coinciden to delet"); false 
    }
   return deleted
 }
  
  def delete_shop(shop : Shop) : Option[DBObject] = {
    Logger.info("[MONGO ADAPTER] Deleting Shop: " + shop.toString())
    val query = MongoDBObject("address" -> shop.address,
                              "name" -> shop.name)
    val result = mongoDB("shops").findAndRemove(query)
    return result
  }
  
  def delete_found_price(found_price : FoundPrice) : Option[DBObject] = {
    Logger.info("[MONGO ADAPTER] Deleting Found-Price: " + found_price.toString())
    val query = MongoDBObject("product_id" -> found_price.product_id,
                              "shop_id" -> found_price.shop_id) 
    
    val result = mongoDB("found_prices").findAndRemove(query)

    return result
  }
  
  def get_or_creat(o : Object) : (Boolean,MongoDBObject) = o match {  
    
     case o: Shop => get_or_create_shop(o)
     case o: FoundPrice => get_or_create_found_price(o)
}
  
  def get_or_create_shop(shop : Shop) : (Boolean,MongoDBObject) = {
    
    Logger.info("[MONGO ADAPTER] Getting or creating Shop: " + shop.toString());
    val query = MongoDBObject("address" -> shop.address,
                              "name" -> shop.name)
    val result = mongoDB("shops").findOne(query) 
   
    result match {
      
      case Some(dbObject) => Logger.info("[MONGO ADAPTER] The Shop was created.");
                             return (false,dbObject)
      case None => Logger.info("[MONGO ADAPTER] Returning the Shop that was already created.");
                   val shop_mongo_object = toMongoDBObjetc(shop);
                   insert_document("shops",shop_mongo_object); 
                   return (true,shop_mongo_object)
      
    }
   
  }
  
  def get_or_create_found_price(found_price : FoundPrice) : (Boolean,MongoDBObject) = {
     Logger.info("[MONGO ADAPTER] Getting or creating Found-Price: " + found_price.toString());
    val query = MongoDBObject("product_id" -> found_price.product_id,
                              "shop_id" -> found_price.shop_id) 

    val result = mongoDB("found_prices").findOne(query)  
    result match {
      
      case Some(dbObject) => Logger.info("[MONGO ADAPTER] The Found-Price was created.");
                             return (false,dbObject)
      case None => Logger.info("[MONGO ADAPTER] Returning TH Found-Price that was already created.");
                   val shop_mongo_object = toMongoDBObjetc(found_price);
                   insert_document("found_prices",shop_mongo_object); 
                   return (true,shop_mongo_object)
      
    }
    
   
  }

  def get_all_found_prices() : List[FoundPrice] = {
    val found_prices_mongo = get_all_found_prices_mongo(None,None)
    var found_prices = List[FoundPrice]()
    for { x <- found_prices_mongo} found_prices = toFoundPrice(x) :: found_prices;
    return found_prices
  }
  
  def get_all_found_prices_mongo(limit:Option[Int],offset:Option[Int]) : List[MongoDBObject] = {
    Logger.info("[MONGO ADAPTER] Getting All Found-Prices.");
    var limitt = limit.getOrElse(100)
    if( limitt > 100 ) limitt = 100;

    val cursor = mongoDB("found_prices").find().sort( MongoDBObject( "_id" -> -1 )).skip( offset.getOrElse(0) ).limit( limitt );
    var list_intermediat = List[MongoDBObject]()
    for { x <- cursor} list_intermediat = x :: list_intermediat;
    var found_prices = List[MongoDBObject]()
    for { x <- list_intermediat} { x.put("id", x.asInstanceOf[MongoDBObject].get("_id").get.toString()); x.remove("_id"); found_prices = x :: found_prices}
    return found_prices
    
  }
  
  def get_all_shops() : List[Shop] = {
    val mongo_shops = get_all_shops_mongo()
    var shops = List[Shop]()
    for { x <- mongo_shops} shops = toShop(x) :: shops;
    return shops
  }
  
  def get_all_shops_mongo() : List[MongoDBObject] = {
    Logger.info("[MONGO ADAPTER] Getting All Shops.");
    val cursor = mongoDB("shops").find()
    var shops = List[MongoDBObject]()
    for { x <- cursor} { x.put("id", x.asInstanceOf[MongoDBObject].get("_id").get.toString()); x.remove("_id"); shops = x :: shops}
    return shops
  }
  
  def get_all_shops_mongo_for(name:Option[String], location:Option[String], latitude:Option[Double], longitude:Option[Double], address:Option[String], limit:Option[Int],offset:Option[Int]) : List[MongoDBObject] = {
    val mongo_shops = get_all_shops_for_mongo(name,location,latitude,longitude, address, limit, offset)
    var shops = List[MongoDBObject]()
    for { x <- mongo_shops} { x.put("id", x.asInstanceOf[MongoDBObject].get("_id").get.toString()); x.remove("_id"); shops = x :: shops}
    return shops
  }
  
  def get_all_shops_for(name:Option[String], location:Option[String], latitude:Option[Double], longitude:Option[Double], address:Option[String], limit:Option[Int],offset:Option[Int]) : List[Shop] = {
    val mongo_shops = get_all_shops_for_mongo(name,location,latitude,longitude, address, limit, offset)
    var shops = List[Shop]()
    for { x <- mongo_shops} shops = toShop(x) :: shops;
    return shops
  }

  def get_all_shops_for_mongo(name:Option[String], location:Option[String], latitude:Option[Double], longitude:Option[Double], address:Option[String], limit:Option[Int],offset:Option[Int]) : List[MongoDBObject] = {
    val builderRapper = new BuilderRapper()
    var limitt = limit.getOrElse(100)
    if( limitt > 100 ) limitt = 100;
    make_mongodb_builder("name",name,builderRapper)
    make_mongodb_builder("location",location,builderRapper)
    make_mongodb_builder("latitude",latitude,builderRapper)
    make_mongodb_builder("longitude",longitude,builderRapper)
    make_mongodb_builder("address",address,builderRapper)
    val q = builderRapper.builder.result
    val cursor = mongoDB("shops").find(q).sort( MongoDBObject( "_id" -> -1 )).skip( offset.getOrElse(0) ).limit( limitt );
    var shops = List[MongoDBObject]()
    for { x <- cursor} shops = x :: shops;
    return shops
  }
  
  def make_mongodb_builder(parameterName:String, parameterValue: Option[_], builderRapeper : BuilderRapper) : BuilderRapper = parameterValue match {
    case None => builderRapeper
    case Some(parValue)    => builderRapeper.builder+= parameterName -> parValue; builderRapeper
  }
  
  def get_by_id_from_collection(id: String,collection:String) : DBObject =  {
    val oid = new ObjectId(id)
    val query : DBObject = MongoDBObject("_id" -> oid)
    val cursor = mongoDB(collection).findOne(query)
    return cursor.getOrElse(DBObject())
    
  }
  
  def get_shop_by_id(id: String) : DBObject = {
    Logger.info("[MONGO ADAPTER] Getting Shop by id. " + id);
    return get_by_id_from_collection(id,"shops")
    
    
  }
  
  def get_found_price_by_id(id: String) : DBObject = {
    Logger.info("[MONGO ADAPTER] Getting Found-Price by id. " + id);
    return get_by_id_from_collection(id,"found_prices")   
  }
  
}
