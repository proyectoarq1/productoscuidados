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
import play.api.libs.json._
import scala.util.parsing.json.JSON._
import play.api.mvc._


class ShopController extends Controller {
  
  var uri = current.configuration.getString("mongodb.uri").get
  var adapter = new MongoAdapter(uri)

  
  def getShops = Action {
    Ok(com.mongodb.util.JSON.serialize(adapter.get_all_shops_mongo()))
  }
  
  def getShopsFor(name: Option[String], location: Option[String], latitude: Option[Double], longitude: Option[Double], address: Option[String], limit:Option[Int],offset:Option[Int]) = Action {
    val offset_query = offset.getOrElse(0)
    var limitt = limit.getOrElse(100)
    if( limitt > 100 ) limitt = 100;
    Logger.info("[SHOPS] Getting filtered Shops with limit: " + limitt.toString + ", offset: " + offset_query.toString );
    val result = com.mongodb.util.JSON.serialize(adapter.get_all_shops_mongo_for(name,location,latitude,longitude, address, limit, offset))
    val response = Json.obj("paging" -> Json.obj("offset" -> offset_query,
                                    "limit" -> offset_query,
                                     "total" -> 0),
                             "items" -> Json.parse(result))
    
    Ok(response)
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
    Logger.info("[SHOPS] Creating new Shop");

    val shop = shopForm.bindFromRequest.get
    val (a, saved) = adapter.get_or_creat(shop)
    val url = """/api/v1/shops/"""+saved.get("_id").get.toString()
    Status(201)(views.html.newShop("Nuevo negocio")).withHeaders("location" -> url)
  }
  
  
  def showShop(id: String) = Action {
    Logger.info("[SHOPS] Showing Shop location with id: " + id );
    val shop = adapter.get_shop_by_id(id)
    Ok(com.mongodb.util.JSON.serialize(shop))
    
  }
  
}


  
  
