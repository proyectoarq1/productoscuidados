package backend

case class ProductDetail (brand: String, 
                          type_product : String = "product", 
                          amount: Double = 1.0, 
                          type_of_capability: Option[String] = None, 
                          type_of_container: Option[String] = None) {

   
    override def toString(): String = "Produc detail: " + brand;
    
 
}