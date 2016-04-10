package backend

case class FoundPrice (the_product_id: String, a_price : Double, date_time: String, a_shop_id : String ) {

    var product_id : String = the_product_id
    var price : Double = a_price
    var shop_id : String = a_shop_id 
    var datetime : String = date_time
    
    override def toString(): String = "Produc: " + product_id + " $" +  price + " in: " + shop_id;

 
}