package backend
import com.mongodb.casbah.Imports._

case class Shop(latitude: Double, longitude:Double, address:String, location:String, name:String)
{
  var uuid : String = ""
  
  override def toString(): String = "Shop: " + name +", " + location + " ("+latitude+","+longitude+") !";
  
  def set_uuid(the_uuid:String) = uuid=the_uuid
}
