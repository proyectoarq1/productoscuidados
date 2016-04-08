package backend
import com.mongodb.casbah.Imports._

case class Negocio(latitud: String, longitud:String, direccion_negocio:String, ciudad_negocio:String, nombre_negocio:String)
{

  var lat: String = latitud
  var lng: String = longitud
  var direccion: String = direccion_negocio
  var ciudad : String = ciudad_negocio
  var nombre : String = nombre_negocio
  
  override def toString(): String = "Negocio: " + nombre +", " + ciudad + " ("+lat+","+lng+")";
  
}