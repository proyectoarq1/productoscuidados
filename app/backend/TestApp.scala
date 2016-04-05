package backend
import backend.MongoAdapter

object TestApp extends App {   
  val a = new MongoAdapter("localhost",27017, "test")
  var negocio = new Negocio("asasa", "long", "calle falsa 123", "ciudad", "Un negocio")
  var precio = new PrecioRegistrado ("codigo", 23.3, negocio, "3/4/2018" ) 
  
  
  println(negocio)
  println(precio)
  
  println(a.toMongoDBObjetc(negocio))
  println(a.toMongoDBObjetc(precio))
  
  a.insert_document("test_collection_negocio",a.toMongoDBObjetc(negocio))
  a.insert_document("test_collection_precio",a.toMongoDBObjetc(precio))

}