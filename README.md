[![Stories in Ready](https://badge.waffle.io/proyectoarq1/productoscuidados.svg?label=ready&title=Ready)](http://waffle.io/proyectoarq1/productoscuidados)

## Requisitos

+ [MongoDB 3.2.4]
+ [Scala 2.11]
+ [Play 2.4] 
+ [git]

## Pasos

+ Una vez instalados los requisitos, clonar el proyecto mediante git (o bien descargar el código de la aplicación)  


```
git clone https://github.com/proyectoarq1/productoscuidados.git
```

+ Posicionarse en la carpeta raiz del proyecto

```
cd productoscuidados/
```

+ Instalar dependencias del proyecto

```
>> activator
```

+ Levantar la aplicación

```
>> run
```

##Link a app en heroku:

http://aqueous-dusk-80720.herokuapp.com/

## Consultas que se le puenden hacer a la api

### Obtener negocios

```GET     /shops```                

Devuelve un json con todos los shops que hay guardados en la base de datos.

### Agregar negocio

```POST	/shops```

Crea un nuevo shop y lo agrega a la base de datos.

### Precios

```GET     /found-prices```

Devuelve un json con todos los precios encontrados que hay guardados en la base de datos.

### Agregar precio econtrado

```POST	/found-prices```

Crea un nuevo precio encontrado y lo guarda en la base de datos.

## Formularios para testear post 
Simplemente son formularios basicos con los campos a rellenar y cun botón para enviar el mismo y realizar el post a las anteriores url mencionadas.

### Get nuevo negocio
```GET 	/newshop```
Formulario para crear un nuevo negocio.


### Get nuevo precio encontrado
```GET 	/newfoundprice```
Formulario para crear un nuevo precio encontrado.




[MongoDB 3.2.4]: (https://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)
[Scala 2.11]:()
[Play 2.4]:(https://www.playframework.com/documentation/2.4.x/Installing) 
[git]:(https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
