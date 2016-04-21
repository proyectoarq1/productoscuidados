package controllers

import play.api._
import play.api.mvc._
import backend._
import backend.Shop
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._ 
import play.api.Play.current

class Application extends Controller {
  
  var uri = current.configuration.getString("mongodb.uri").get
  var adapter = new MongoAdapter(uri)

  

  def index = Action {
    Ok(views.html.index("Productos Cuidados API."))
  }
  
  def getShops = Action {
    Ok(com.mongodb.util.JSON.serialize(adapter.get_all_shops_mongo()))
  }
  
  def getShopsFor(name: Option[String], location: Option[String], latitude: Option[Double], longitude: Option[Double], address: Option[String]) = Action {
    Ok(com.mongodb.util.JSON.serialize(adapter.get_all_shops_for(name,location,latitude,longitude, address)))
  }

  val shopForm : Form[Shop] = Form (
    mapping(
      "latitude"-> of[Double],
      "longitude"-> of[Double],
      "address"-> text, 
      "location"-> text,
      "name" -> text
      
    )(Shop.apply)(Shop.unapply)
    
  )
  
  def newShop = Action {
    Ok(views.html.newShop("Nuevo negocio"))
  }
  
  def addShop = Action { implicit request =>
    val shop = shopForm.bindFromRequest.get
    val (a, saved) = adapter.get_or_creat(shop)
    val url = """\shops\"""+saved.get("_id").get.toString()
    Ok(views.html.created(url))
  }
  
  def getFoundPrices = Action {
    Ok(com.mongodb.util.JSON.serialize(adapter.get_all_found_prices_mongo()))
  }
  
  val foundPriceForm : Form[FoundPrice] = Form (
    mapping(
      "product_id"-> text,
      "price"-> of[Double],
      "datetime"-> text,
      "shop_id" -> text
    )(FoundPrice.apply)(FoundPrice.unapply)
    
  )
  
  def newFoundPrice = Action {
    Ok(views.html.newFoundPrice("Nuevo precio registrado"))
  }
  
  def addFoundPrice = Action { implicit request =>
    val found_price = foundPriceForm.bindFromRequest.get
    val (a, saved) = adapter.get_or_creat(found_price)
    val url = """\found-prices\"""+saved.get("_id").get.toString()
    Ok(views.html.created(url))
  }

}
