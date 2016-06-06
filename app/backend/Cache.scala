package backend
import org.joda.time.DateTime
import org.joda.time.Minutes
import scala.util.parsing.json.JSON._
import play.api.Logger
import play.api.Play.current
import scala.util.Try

object Cache {
  
  var cache_enabled = Try((current.configuration.getString("cacheenabled").get).toBoolean).getOrElse(false)
  var expired_time_minutes : Int = 4 
  var cache = collection.mutable.Map[String, (String, DateTime)]()
  
  def result_expired(date : DateTime) : Boolean  = {return true}
  
  def save_result(key : String, result: String) = {
    if (cache_enabled) {
      Logger.info("[CACHE] Saving result for key " + key + " in the cache" );
      cache.update(key, (result, DateTime.now))
      }
    }
  
  def delete_result( key : String) = {
    if (cache_enabled) {
      Logger.info("[CACHE] Deleting result for key " + key + " from the cache" );
      cache.remove(key)
      }
    }
  
  def search_result( key : String) : Option[String]= { 
    if (cache_enabled && cache.contains(key)) {
      val (result, date) = cache.get(key).get
      val hours = Minutes.minutesBetween(date, DateTime.now).getMinutes
      if (hours <= expired_time_minutes){
        Logger.info("[CACHE] Retrieving result for key " + key + " from the cache" );
        return Some(result)
      } 
      else { Logger.info("[CACHE] The result for key " + key + " from the cache has been expired" ); }
    }    
    return None
  }
  
  
}

