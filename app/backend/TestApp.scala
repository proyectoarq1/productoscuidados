package backend
import org.bson.types.ObjectId
import play.api.Play.current

object TestApp extends App {   
  val a = new MongoAdapter("mongodb://localhost:27017/test")
  var shop = new Shop(13.56, 56.78, "calle falsa 123", "ciudad", "Un shop")
  var shop2 = new Shop(14.56, 26.78, "av siempre viva", "ciudad", "shop 2")
  val (value, shoped) = a.get_or_create_shop(shop)
  shop.set_uuid(shoped.get("_id").get.toString())
  
  var price = new FoundPrice ("codigo", 23.3, "3/4/2018",shoped.get("_id").get.toString(),None) 

  println(shop)
  println(price)
  
  println(a.toMongoDBObjetc(shop))
  println(a.toMongoDBObjetc(price))
  
  a.insert_document("shops",a.toMongoDBObjetc(shop))
  a.insert_document("prices_registrados",a.toMongoDBObjetc(price))
  
  //a.get_found_price_by_id(price.get("_id").get.toString())
  println("aca esta la posta")
  println(a.get_shop_by_id(shoped.get("_id").get.toString()))
  
  println(a.get_or_create_shop(shop))
  println(a.get_or_create_shop(shop2))
  println(a.get_or_create_found_price(price))
  println("-----------------")
  println(a.get_all_shops_for(None,None,Some(14.56),Some(26.78),None,None,None))
  println(a.get_all_shops_for(None,None,None,Some(26.78),Some("av siempre viva"),None,None).length)
  //println(current.configuration.getString("db.mongouri"))
  println("-----------------")
  
  price = new FoundPrice ("codigo2", 23.3, "3/4/2018",shoped.get("_id").get.toString(), None)
  println("resultadoooo")
  a.get_shop_by_id(shoped.get("_id").get.toString())
  println(a.get_or_create_found_price(price))
  println(a.get_or_create_found_price(price))
  
  println(a.delete(price))
  println(a.delete(price))
  
  println(a.get_all_shops())
  println(a.get_all_found_prices())
  
  //a.drop_collection("shops")
  //a.drop_collection("found_prices")
  

}