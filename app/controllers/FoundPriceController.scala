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


class FoundPriceController extends Controller {
  
  var uri = current.configuration.getString("mongodb.uri").get
  var adapter = new MongoAdapter(uri)

  
  
  def getFoundPrices(limit:Option[Int],offset:Option[Int]) = Action {
    val offset_query = offset.getOrElse(0)
    var limitt = limit.getOrElse(100)
    if( limitt > 100 ) limitt = 100;
    Logger.info("[FOUND-PRICES] Getting Found-Prices with limit: " + limitt.toString + ", offset: " + offset_query.toString );
    val result = com.mongodb.util.JSON.serialize(adapter.get_all_found_prices_mongo(limit,offset))
   
    
    val response = Json.obj("paging" -> Json.obj("offset" -> offset_query,
                                    "limit" -> offset_query,
                                     "total" -> 0),
                             "items" -> Json.parse(result)) 
    Ok(response)
  }

  val foundPriceForm : Form[FoundPrice] = Form (
    mapping(
      "product_id"-> text,
      "price"-> of[Double],
      "datetime"-> text,
      "shop_id" -> text,
      "product_detail" -> optional(mapping(
        "brand" -> text,
        "type_product" -> text,
        "amount" -> of[Double],
        "type_of_capability" -> optional(text),
        "type_of_container" -> optional(text)
    )(ProductDetail.apply)(ProductDetail.unapply))
    )(FoundPrice.apply)(FoundPrice.unapply)
    
  )
  
  def newFoundPrice = Action {
    Ok(views.html.newFoundPrice("Nuevo precio registrado"))
  }
  
  def addFoundPrice = Action { implicit request =>
    Logger.info("[FOUND-PRICES] Creating new Found-Price");
    val found_price = foundPriceForm.bindFromRequest.get
    println(request.body)
    println(found_price.product_detail)
    val (a, saved) = adapter.get_or_creat(found_price)
    val url = """/api/v1/found-prices/"""+saved.get("_id").get.toString()

    Status(201)(views.html.created(url)).withHeaders("location" -> url)
    //Ok(views.html.created(url))
  }

  def showFounfPrice(id: String) = Action {
    Logger.info("[FOUND-PRICES] Showing Found-Price location with id: " + id);
    val found_price = adapter.get_found_price_by_id(id)
    Ok(com.mongodb.util.JSON.serialize(found_price))
    //return found_price
    
  }

}


