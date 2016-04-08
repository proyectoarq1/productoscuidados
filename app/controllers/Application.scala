package controllers

import play.api._
import play.api.mvc._
import backend._
import backend.Negocio
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._ 

class Application extends Controller {
  
  var adapter = new MongoAdapter("localhost",27017, "test")

  

  def index = Action {
    Ok(views.html.index("Your new application is reeady."))
  }
  
  def getNegocios = Action {
    Ok(com.mongodb.util.JSON.serialize(adapter.get_all_negocios_mongo()))
  }

  val negocioForm : Form[Negocio] = Form (
    mapping(
      "lat"-> text,
      "lng"-> text,
      "direccion"-> text, 
      "ciudad"-> text,
      "nombre" -> text
      
    )(Negocio.apply)(Negocio.unapply)
    
  )
  
  def nuevoNegocio = Action {
    Ok(views.html.nuevoNegocio("Nuevo negocio"))
  }
  
  def agregarNegocio = Action { implicit request =>
    val negocio = negocioForm.bindFromRequest.get
    val a, guardado = adapter.get_or_creat(negocio)
    println(a)
    println(negocio)
    println(guardado)
    Redirect(routes.Application.getNegocios())
  }
  
  def getPreciosRegistrados = Action {
    Ok(com.mongodb.util.JSON.serialize(adapter.get_all_precios_registrados_mongo()))
  }
  
  val precioRegistradoForm : Form[PrecioRegistrado] = Form (
    mapping(
      "codigo"-> text,
      "precio"-> of[Double],
      "negocio"-> null, 
      "fecha_hora"-> text 
    )(PrecioRegistrado.apply)(PrecioRegistrado.unapply)
    
  )
  
  def nuevoPrecioRegistrado = Action {
    Ok(views.html.nuevoPrecioRegistrado("Nuevo precio registrado"))
  }
  
  def agregarPrecioRegistrado = Action { implicit request =>
    val precio_registrado = precioRegistradoForm.bindFromRequest.get
    val a, guardado = adapter.get_or_creat(precio_registrado)
    println(a)
    println(precio_registrado)
    println(guardado)
    Redirect(routes.Application.getNegocios())
  }

}
