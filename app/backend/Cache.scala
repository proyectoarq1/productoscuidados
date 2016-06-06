package backend
import org.joda.time.DateTime
import org.joda.time.Minutes
import scala.util.parsing.json.JSON._
import play.api.Logger

object Cache {
  
  var expired_time_hours : Int = 4 
  var cache = collection.mutable.Map[String, (String, DateTime)]()
  
  def result_expired(date : DateTime) : Boolean  = {return true}
  
  def save_result(key : String, result: String) = {
    Logger.info("[CACHE] Saving result for key " + key + " in the cache" );
    cache.update(key, (result, DateTime.now))
    }
  
  def delete_result( key : String) = {
    Logger.info("[CACHE] Deleting result for key " + key + " from the cache" );
    cache.remove(key)
    }
  
  def search_result( key : String) : Option[String]= { 
    if (cache.contains(key)) {
      Logger.info("[CACHE] Retrieving result for key " + key + " from the cache" );
      val (result, date) = cache.get(key).get
      val hours = Minutes.minutesBetween(date, DateTime.now).getMinutes 
      if (hours <= expired_time_hours){
        Logger.info("[CACHE] The result for key " + key + " from the cache has been expired" );
        return Some(result)
      } 
    }    
    return None
  }
  
  
}

