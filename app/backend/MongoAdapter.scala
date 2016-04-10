package backend
import com.mongodb.casbah.Imports._

class MongoAdapter(db_host : String, db_port: Int, db_selected: String) {
  
  var host: String = db_host
  var port: Int = db_port
  var db: String = db_selected
  
  var mongoClient = MongoClient(db_host, db_port)
  var mongoDB = mongoClient(db_selected)
  
  override def toString(): String = "Mongo adapter conected to " + host + ":" + port + "/" + db_selected;

  
  def insert_document(colection_name : String, document : MongoDBObject) = mongoDB(colection_name).insert(document);  
  def drop_collection(colection_name : String) = mongoDB(colection_name).drop();

  def toFoundPrice(found_price: MongoDBObject) : FoundPrice = {

    val product_id = found_price.getAs[String]("product_id").get
    val price =  found_price.getAs[Double]("price").get
    val datetime =  found_price.getAs[String]("datetime").get
    val shop_id =  found_price.getAs[String]("shop_id").get
    
    return new FoundPrice(product_id, price, datetime, shop_id)
    
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
                                             "datetime" -> o.datetime)
 }
  
  def delete(o : Object) : Boolean = {
    
    val result = o match {
      case o: Shop => delete_shop(o);
      case o:FoundPrice => delete_found_price(o)
    }
   val deleted = result match {
      case Some(l) => true
      case None => false 
    }
   return deleted
 }
  
  def delete_shop(shop : Shop) : Option[DBObject] = {
    val query = MongoDBObject("address" -> shop.address,
                              "name" -> shop.name)
    val result = mongoDB("shops").findAndRemove(query)
    return result
  }
  
  def delete_found_price(found_price : FoundPrice) : Option[DBObject] = {
    
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
    
    val query = MongoDBObject("address" -> shop.address,
                              "name" -> shop.name)
    val result = mongoDB("shops").findOne(query) 
   
    result match {
      
      case Some(dbObject) => return (false,dbObject)
      case None => val shop_mongo_object = toMongoDBObjetc(shop);
                   insert_document("shops",shop_mongo_object); 
                   return (true,shop_mongo_object)
      
    }
   
  }
  
  def get_or_create_found_price(found_price : FoundPrice) : (Boolean,MongoDBObject) = {
    
    val query = MongoDBObject("product_id" -> found_price.product_id,
                              "shop_id" -> found_price.shop_id) 

    val result = mongoDB("found_prices").findOne(query)  
    result match {
      
      case Some(dbObject) => return (false,dbObject)
      case None => val shop_mongo_object = toMongoDBObjetc(found_price);
                   insert_document("found_prices",toMongoDBObjetc(found_price)); 
                   return (true,shop_mongo_object)
      
    }
    
   
  }

  def get_all_found_prices() : List[FoundPrice] = {
    val found_prices_mongo = get_all_found_prices_mongo()
    var found_prices = List[FoundPrice]()
    for { x <- found_prices_mongo} found_prices = toFoundPrice(x) :: found_prices;
    return found_prices
  }
  
  def get_all_found_prices_mongo() : List[MongoDBObject] = {
    
    val cursor = mongoDB("found_prices").find()
    var found_prices = List[MongoDBObject]()
    for { x <- cursor} found_prices = x :: found_prices;
    return found_prices
    
  }
  
  def get_all_shops() : List[Shop] = {
    val mongo_shops = get_all_shops_mongo()
    var shops = List[Shop]()
    for { x <- mongo_shops} shops = toShop(x) :: shops;
    return shops
  }
  
  def get_all_shops_mongo() : List[MongoDBObject] = {
    
    val cursor = mongoDB("shops").find()
    var shops = List[MongoDBObject]()
    for { x <- cursor} shops = x :: shops;
    return shops
  }
  
}
