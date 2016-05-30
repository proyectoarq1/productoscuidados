[![Stories in Ready](https://badge.waffle.io/proyectoarq1/productoscuidados.svg?label=ready&title=Ready)](http://waffle.io/proyectoarq1/productoscuidados)

## Requisitos

+ [MongoDB 3.2.4](https://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)
+ [Scala 2.11](http://www.scala-lang.org/download/)
+ [Play 2.4](https://www.playframework.com/documentation/2.4.x/Installing) 
+ [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
+ [docker 1.11.1](https://docs.docker.com/engine/installation/)

## Pasos para levantar la aplicación

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

## Consultas que se le pueden hacer a la api

### Obtener todos los negocios

```GET     /api/v1/shops/all```                

Devuelve un json con todos los shops que hay guardados en la base de datos.

### Obtener negocios por características

```GET     /api/v1/shops```                

Parámetros: latitude, longitude, name, location, address.  
Devuelve un json con todos los shops guardados en la base de datos que cumplen con las características ingresadas en los parámetros.  

### Agregar negocio

```POST	/api/v1/shops```

Parámetros: latitude (\*), longitude (\*), name, location, address   
Crea un nuevo shop y lo agrega a la base de datos.  

(*) obligatorios

### Precios

```GET     /api/v1/found-prices```

Devuelve un json con todos los precios encontrados que hay guardados en la base de datos.

### Agregar precio econtrado

```POST	   /api/v1/found-prices```

Parámetros: product_id, price, shop_id, datetime
Crea un nuevo precio encontrado y lo guarda en la base de datos.

## Formularios para testear post 
Simplemente son formularios basicos con los campos a rellenar y un botón para enviar el mismo y realizar el post a las anteriores url mencionadas.

### Get nuevo negocio
```GET 	   /api/v1/newshop```  

Formulario para crear un nuevo negocio.


### Get nuevo precio encontrado
```GET 	  /api/v1/newfoundprice```  

Formulario para crear un nuevo precio encontrado.

## Docker
La aplicación tiene la opción de poder correr con docker sobre la imagen base "beevelop/java:latest"(Ubuntu 15.10-java8) y con una base de datos mongo. Para esto se generan 2 imágenes: Una para la base de datos y otra para la aplicación en si. Luego estas imágenes son linkeadas.
Activator tiene integración con docker por lo que te deja customizar la imagen en el build.sbt de forma amena. Además de darte comandos para ayudarte a generarla:
```
activator docker:stage - evalúa el build.sbt y genera el dockerfile correspondiente.
activator docker:publishLocal - genera la imágen docker apartir del docker file antes generado.
```
Hay más comandos, pero estos 2 son los que particularmente nosotros usamos.

Para correr la app en docker sólo hay que ejecutar el script:
```
sh docker-play mongodbimage
```
donde "mongodbimage" es el nombre que va a tener la imagen de la base mongo(esto se parametrizó para hacer pruebas y así poder usar la misma imagen mongo para distintas imágenes de la aplicación).

Una vez ejecutado ese script la aplicación ya está corriendo.
Además se le a agregado una integración con New Relic a la imagen generada por lo que las métricas y análisis los vemos ahí.

##Link a app en heroku:

http://aqueous-dusk-80720.herokuapp.com/

-------------------------------
