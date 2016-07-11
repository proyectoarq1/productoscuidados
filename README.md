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

## Monitoreo
La aplicación en heroku y la que corre en docker tienen integración con [New Relic](https://newrelic.com/) que nos brinda distintos tipos de métricas en relación al uso de la aplicación.

## Docker
La aplicación tiene la opción de poder correr con docker sobre la imagen base "beevelop/java:latest"(Ubuntu 15.10-java8) y con una base de datos mongo. Para esto se generan 2 imágenes: Una para la base de datos y otra para la aplicación en si. Luego estas imágenes son linkeadas.
Activator tiene integración con docker por lo que te deja customizar la imagen en el build.sbt de forma amena. Además de darte comandos para ayudarte a generarla:
```
activator docker:stage - evalúa el build.sbt y genera el dockerfile correspondiente.
activator docker:publishLocal - genera la imágen docker apartir del dockerfile antes generado.
```
Hay más comandos, pero estos 2 son los que particularmente usamos nosotros.

Para tener corriendo la app en docker hay que ejecutar 2 comandos:
```
sh generate-docker-play.sh
sh run-docker-play.sh
```
El primero nos generará la imagen del proyecto. Y el segundo levantará la imagen de mongo(de ser necesario) y la imagen del proyecto.
Es importante que antes de correr el primer comando que corre el script del generate sepamos cuantas bases de datos vamos a querer usar. Las opcciones son:
  - Un nodo: Una base de datos mongo
  - Dos nodos: Una base de datos mongo y una replica
  - Tres nodos: Una base de datos mongos y dos replicas
Una vez que sepamos esto debemos editar el archivo de configuración de la aplicación de docker ubicado en la carpeta conf/ llamado application.docker.conf. Hay tres urls posibles de usar para la conección con mongo:
```
#Con un nodo
mongodb.uri ="mongodb://mongoplay:27017/test"
#____________________________

#Con dos nodos
#mongodb.uri ="mongodb://mongo1,mongo2:27017/test"
#____________________________

#Con tres nodos
#mongodb.uri ="mongodb://mongo1,mongo2,mongo3:27017/test"
```
Si no modificasemos nada y corrieramos el comando directamente esto tendría la url de conección sólo para una base de datos sin replica (el mongodb.uri descomentado es "mongodb://mongoplay:27017/test", que arriba tiene el título de "#Con un nodo"). Para elegir la que queremos levantar (un nodo, dos nodos o bien tres nodos) debemos descomentar mondodb.uri que deseemos usar (finandonos en el titulo cual corresponde) y comendar los demás.
El 2do script además nos ofrece la posibilidad de definir los recursos que va a tener el contenedor donde se levantará la imagen del proyecto. Para esto al correr el script te preguntará si quieres realizar esta configuración o si corre con los valores por defecto. Al optar por sí hacerlo se puede elegir también cuantos nodos va a tener la base de datos (por defecto es uno sólo).

Una vez ejecutado ese script la aplicación ya está corriendo.
Además se le a agregado una integración con New Relic a la imagen generada por lo que las métricas y análisis los vemos ahí.

## Test de carga
Para ello utilizamos la herramienta [Locust](http://locust.io/)

### Requisitos

+ [Python 2.7](https://www.python.org/downloads/release/python-2711/)
+ [pip](https://pip.pypa.io/en/stable/installing/)
+ [Locust io](http://docs.locust.io/en/latest/installation.html) 


### Correr los test

#### Corrida simple y local, sin rampa
##### Levantar locust
Para correr los test de carga realizados por locus hay que posicionarse con la consola en la carpeta raíz del proyecyo y luego ejecutar el siguiente comando
```
locust -f locustfile.py --host=http://host:port
```
Donde host es el 'host' sobre el que corre nuestra aplicación y 'port' es dicho puerto.

##### Empezar el test

Entrar en la dirección http://localhost:8089/ en un navegador web e indicar cuantos usarios se quieren simular en total (campo "Number of users to simulate") y cuantos quiere se inicien por segundo (campo "Hatch rate (users spawned/second)"). Una vez hecho esto pulsar el botón "Start Swarming". En la ventana podrán verse datos de como va corriendo el test y el mismo correra hasta que se pulse el botón de stop.

### Herramienta extra para test de carga
Con locust no pudimos configurar períodos de rampa en los tests, para suplir esta característica usamos como complemento [Taurus](http://gettaurus.org/)
Se instala de manera muy sencilla con el comando: 
```
sudo pip install bzt
```
Taurus te pide definir un archivo yml donde hay configuración extra del test de carga que va a correr. Para ello le pasamos el locustfile.py mencionado más arriba.
Para correr los test de carga ahora con la configuración extra de Taurus hay que correr el comando explicado en el menú más arriba llamado 'Levantar Locust' pero en vez de los pasos que dice el menú 'Empezar el test' hay que correr el siguiente comando:
```
bzt test.yml
```
Donde 'test.yml' es el archivo que configuramos de Taurus.
Esto nos mostrará en consola unos gráficos sobre qué es lo que está pasando durante el test además de generarte en el proyecto una carpeta con la fecha y hora de corrida del test y una serie de reportes tales como la lista de request, los porcetajes, tiempos, etc.

##Link a app en heroku:

http://aqueous-dusk-80720.herokuapp.com/


-------------------------------
