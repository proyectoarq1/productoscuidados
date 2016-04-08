package backend

object TestApp extends App {   
  val a = new MongoAdapter("localhost",27017, "test")
  var negocio = new Negocio("asasa", "long", "calle falsa 123", "ciudad", "Un negocio")
  var precio = new PrecioRegistrado ("codigo", 23.3, negocio, "3/4/2018" ) 
  
  
  println(negocio)
  println(precio)
  
  println(a.toMongoDBObjetc(negocio))
  println(a.toMongoDBObjetc(precio))
  
  a.insert_document("negocios",a.toMongoDBObjetc(negocio))
  a.insert_document("precios_registrados",a.toMongoDBObjetc(precio))
  
  println(a.get_or_create_negocio(negocio))
  println(a.get_or_create_precio_registrado(precio))
  
  precio = new PrecioRegistrado ("codigo2", 23.3, negocio, "3/4/2018" )
  println(a.get_or_create_precio_registrado(precio))
  println(a.get_or_create_precio_registrado(precio))
  
  println(a.delete(precio))
  println(a.delete(precio))
  
  println(a.get_all_negocios())
  println(a.get_all_precios_registrados())
  
  //a.drop_collection("negocios")
  //a.drop_collection("precios_registrados")
  

}