package backend

case class PrecioRegistrado (cod_barra: String, precio_prod : Double, lugar : Negocio, fecha: String ) {

    var codigo : String = cod_barra
    var precio : Double = precio_prod
    var negocio : Negocio = lugar
    var fecha_hora : String = fecha
    
    override def toString(): String = "Producto: " + codigo + " $" +  precio;

 
}