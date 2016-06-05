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


class Application extends Controller {
  


  

  def index = Action {
    Ok(views.html.index("Productos Cuidados API."))
  }
  


}
