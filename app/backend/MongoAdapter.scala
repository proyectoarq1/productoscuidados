package backend
import com.mongodb.casbah.Imports._

class MongoAdapter(db_host : String, db_port: Int, db_selected: String) {
  
  var host: String = db_host
  var port: Int = db_port
  var db: String = db_selected
  
  var mongoClient = MongoClient(db_host, db_port)
  var mongoDB = mongoClient(db_selected)
  
  override def toString(): String = "Mongo adapter conected to " + host + ":" + port + "/" + db_selected;

  
  def insert_document(colection_name : String, document : MongoDBObject) = mongoDB(colection_name).insert(document);  
  def drop_collection(colection_name : String) = mongoDB(colection_name).drop();

  def toPrecioRegistrado(precio_registrado: MongoDBObject) : PrecioRegistrado = {
    
    val codigo = precio_registrado.getAs[String]("codigo").get
    val precio =  precio_registrado.getAs[Double]("precio").get
    val fecha_hora =  precio_registrado.getAs[String]("fecha_hora").get
    val negocio =  toNegocio(precio_registrado.getAs[MongoDBObject]("negocio").get)
    
    return new PrecioRegistrado(codigo, precio, negocio, fecha_hora)
    
  }
  
  def toNegocio(precio_registrado: MongoDBObject) : Negocio = {
    
    val lat = precio_registrado.getAs[String]("lat").get
    val lng = precio_registrado.getAs[String]("lng").get
    val direccion = precio_registrado.getAs[String]("direccion").get
    val ciudad = precio_registrado.getAs[String]("ciudad").get
    val nombre = precio_registrado.getAs[String]("nombre").get
    
    return new Negocio(lat, lng, direccion, ciudad, nombre)
    
    
    
  }
 

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
  
  def delete(o : Object) : Boolean = {
    
    val result = o match {
      case o: Negocio => delete_negocio(o);
      case o:PrecioRegistrado => delete_precio_registrado(o)
    }
   val deleted = result match {
      case Some(l) => true
      case None => false 
    }
   return deleted
 }
  
  def delete_negocio(negocio : Negocio) : Option[DBObject] = {
    val query = MongoDBObject("ciudad" -> negocio.ciudad,
                              "nombre" -> negocio.nombre)
    val result = mongoDB("negocios").findAndRemove(query)
    return result
  }
  
  def delete_precio_registrado(precio_registrado : PrecioRegistrado) : Option[DBObject] = {
    
    val query = MongoDBObject("codigo" -> precio_registrado.codigo,
                              "negocio.nombre" -> precio_registrado.negocio.nombre,
                              "negocio.ciudad"-> precio_registrado.negocio.ciudad) 
    
    val result = mongoDB("precios_registrados").findAndRemove(query)

    return result
  }
  
  def get_or_creat(o : Object) : (Boolean,MongoDBObject) = o match {  
     case o: Negocio => get_or_create_negocio(o)
     case o: PrecioRegistrado => get_or_create_precio_registrado(o)
}
  
  def get_or_create_negocio(negocio : Negocio) : (Boolean,MongoDBObject) = {
    
    val query = MongoDBObject("ciudad" -> negocio.ciudad,
                              "nombre" -> negocio.nombre)
    val result = mongoDB("negocios").findOne(query) 
   
    result match {
      
      case Some(dbObject) => return (false,dbObject)
      case None => val negocio_mongo_object = toMongoDBObjetc(negocio);
                   insert_document("negocios",negocio_mongo_object); 
                   return (true,negocio_mongo_object)
      
    }
   
  }
  
  def get_or_create_precio_registrado(precio_registrado : PrecioRegistrado) : (Boolean,MongoDBObject) = {
    
    val query = MongoDBObject("codigo" -> precio_registrado.codigo,
                              "negocio.nombre" -> precio_registrado.negocio.nombre,
                              "negocio.ciudad"-> precio_registrado.negocio.ciudad) 

    val result = mongoDB("precios_registrados").findOne(query)  
    result match {
      
      case Some(dbObject) => return (false,dbObject)
      case None => val negocio_mongo_object = toMongoDBObjetc(precio_registrado);
                   insert_document("precios_registrados",toMongoDBObjetc(precio_registrado)); 
                   return (true,negocio_mongo_object)
      
    }
    
   
  }

  def get_all_precios_registrados() : List[PrecioRegistrado] = {
    val precios_registados_mongo = get_all_precios_registrados_mongo()
    var precios_registados = List[PrecioRegistrado]()
    for { x <- precios_registados_mongo} precios_registados = toPrecioRegistrado(x) :: precios_registados;
    return precios_registados
  }
  
  def get_all_precios_registrados_mongo() : List[MongoDBObject] = {
    
    val cursor = mongoDB("precios_registrados").find()
    var precios_registados = List[MongoDBObject]()
    for { x <- cursor} precios_registados = x :: precios_registados;
    return precios_registados
    
  }
  
  def get_all_negocios() : List[Negocio] = {
    val mongo_negocios = get_all_negocios_mongo()
    var negocios = List[Negocio]()
    for { x <- mongo_negocios} negocios = toNegocio(x) :: negocios;
    return negocios
  }
  
  def get_all_negocios_mongo() : List[MongoDBObject] = {
    
    val cursor = mongoDB("negocios").find()
    var negocios = List[MongoDBObject]()
    for { x <- cursor} negocios = x :: negocios;
    return negocios
  }
  
}
