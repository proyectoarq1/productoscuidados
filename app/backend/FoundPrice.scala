package backend

case class FoundPrice (product_id: String, price : Double, datetime: String, shop_id : String, product_detail : Option[ProductDetail] ) {
    
    override def toString(): String = "Produc: " + product_id + " $" +  price + " in: " + shop_id;
 
}