package backend
import org.bson.types.ObjectId

object TestApp extends App {   
  val a = new MongoAdapter("localhost",27017, "test")
  var shop = new Shop(13.56, 56.78, "calle falsa 123", "ciudad", "Un shop")
  val (value, shoped) = a.get_or_create_shop(shop)
  shop.set_uuid(shoped.get("_id").get.toString())
  
  var price = new FoundPrice ("codigo", 23.3, "3/4/2018",shoped.get("_id").get.toString() ) 
  
  println(shop)
  println(price)
  
  println(a.toMongoDBObjetc(shop))
  println(a.toMongoDBObjetc(price))
  
  a.insert_document("shops",a.toMongoDBObjetc(shop))
  a.insert_document("prices_registrados",a.toMongoDBObjetc(price))
  
  println(a.get_or_create_shop(shop))
  println(a.get_or_create_found_price(price))
  
  price = new FoundPrice ("codigo2", 23.3, "3/4/2018",shoped.get("_id").get.toString())
  println(a.get_or_create_found_price(price))
  println(a.get_or_create_found_price(price))
  
  println(a.delete(price))
  println(a.delete(price))
  
  println(a.get_all_shops())
  println(a.get_all_found_prices())
  
  a.drop_collection("shops")
  a.drop_collection("found_prices")
  

}