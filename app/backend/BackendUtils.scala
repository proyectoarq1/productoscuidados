package backend
import scala.math._

class BackendUtils {
  
 
  def distancia(lat1 : String, lng1: String, lat2 : String, lng2: String) : Double = {
    val radio_tierra  = 6371000; //meters
    val distancia_lat =  toRadians(lat2.toDouble-lat1.toDouble)
    val distancia_lng =  toRadians(lng2.toDouble-lng1.toDouble)
    
    val a = sin(distancia_lat/2) * sin(distancia_lat/2) +
            cos(toRadians(lat1.toDouble)) * cos(toRadians(lat2.toDouble)) *
            sin(distancia_lng/2) * sin(distancia_lng/2);
    val c = 2 * atan2(sqrt(a), sqrt(1-a));
    val distancia = radio_tierra * c;

    return distancia
    
  }
  
}
  
  //def parseDouble(s: String) = try { Some(s.toDouble) } catch { case _ => None }
