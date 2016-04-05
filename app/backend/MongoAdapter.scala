package backend
import com.mongodb.casbah.Imports._

class MongoAdapter(db_host : String, db_port: Int, db_selected: String) {
  
  var host: String = db_host
  var port: Int = db_port
  var db: String = db_selected
  
  var mongoClient = MongoClient(db_host, db_port)
  var mongoDB = mongoClient(db_selected)
  
  override def toString(): String = "Mongo adapter conected to " + host + ":" + port + "/" + db_selected;

  
  def insert_document(colection_name : String, document : MongoDBObject) =  mongoDB(colection_name).insert(document);  
  def drop_collection(colection_name : String) = mongoDB(colection_name).drop();

  def toMongoDBObjetc(o : Object) : MongoDBObject = o match {
    case o:Negocio => MongoDBObject("lat" -> o.lat, 
                                    "lng" -> o.lng, 
                                    "direccion" -> o.direccion,
                                    "ciudad" -> o.ciudad,
                                    "nombre" -> o.nombre)
    case o:PrecioRegistrado => MongoDBObject("codigo" -> o.codigo,
                                             "precio" -> o.precio,
                                             "negocio" -> toMongoDBObjetc(o.negocio),
                                             "fecha_hora" -> o.fecha_hora)
                                             
  }

  
}
