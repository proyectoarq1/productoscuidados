package backend
import com.mongodb.casbah.Imports._

case class Shop(lat: Double, lng:Double, a_address:String, a_location:String, a_name:String)
{
  var uuid : String = ""
  var latitude: Double = lat
  var longitude: Double = lng
  var address: String = a_address
  var location : String = a_location
  var name : String = a_name
  
  override def toString(): String = "Shop: " + name +", " + location + " ("+latitude+","+longitude+") !";
  
  def set_uuid(the_uuid:String) = uuid=the_uuid
}
