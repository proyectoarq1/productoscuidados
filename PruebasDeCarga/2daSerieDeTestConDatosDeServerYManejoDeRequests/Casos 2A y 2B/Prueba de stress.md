Prueba de stress 
===================

####Comparativo de:

 - _Caso **2A**: Desempeño de un nodo virtual de aplicación con 1 CPU; y 2 nodos de BD en réplica_
 - _Caso **2B**: Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica_


> **Nota:**
> En ambos casos se ha corrió el test con 500 usuarios concurrentes, con una rampa de aproximadamente 7 minutos y un hold-for de aproximadamente 3 minutos.

----------

Caso **2A**)

- Luego de aproximadamente 5 minutos de comenzar el test de carga el tiempo de respuesta comienza a subir para durante el resto del test no volver a bajar (siendo en ese punto  8.26 ms por request el tiempo de respuesta sobre  5705.9 transacciones)

- Luego de 3 minutos de comenzar a subir el tiempo de respuesta (es decir a los 8 minutos aproximadamente ya dentro del hold-for) se alcanza el pico de request con 13761.8 aproximadamente transacciones en ese minuto.

- Sin embargo durante esta prueba se observa el pico de tiempo de respuesta al minuto de comenzarla con 39 ms  siendo 325.5 las transacciones procesadas en ese momento, sin tener transacciones encoladas.

- Total 109606 transacciones. 

- Máximo uso de memoria y cpu de la imagen de docker: 1,0 CPU y 25 MB 

- Resumen de request:

|Name   |# reqs |# fails|Avg |Min  |Max  |Median   |req/s |
|-------|-------|-------|----|-----|-----|---------|------|
|POST /api/v1/found-prices  |                                   18538  |   0(0.00%) |     59   |    5  |   511  |      43 |  54.70 |
|POST /api/v1/shops         |                                    24427 |    0(0.00%) |     36   |    4  |   460  |      23 |  66.50|
|GET /api/v1/shops?location=Berazategui |                        12247  |   0(0.00%) |     27   |    3  |   508  |      15 |  36.10|
|GET /api/v1/shops?location=Bernal  |                            12160  |   0(0.00%)  |    26  |     3   |  438  |      14  | 32.00 |
|GET /api/v1/shops?location=Lanus |                              12466   |  0(0.00%) |     27   |    3  |   515  |      14 |  35.90|
|-------|-------|-------|----|-----|-----|---------|------|
|Total  |79838  | 0(0.00%) | |     |     |         | 225.20|

- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
|POST /api/v1/found-prices  |                                     18538   |  43  |   61  |   75  |   87  |  120 |   150 |   210 |   280 |   511|
| POST /api/v1/shops    |                                          24427  |   23 |    35  |   45  |   54 |    81  |  110  |  160  |  210 |   460|
|GET /api/v1/shops?location=Berazategui|                          12247  |   15 |    24   |  34   |  41   |  68  |   96   | 130  |  170  |  508|
|GET /api/v1/shops?location=Bernal|                               12160  |   14  |   23  |   32  |   39  |   63  |   91   | 130  |  160  |  438|
|GET /api/v1/shops?location=Lanus |                               12466   |  14   |  24   |  33  |   41  |   69  |   99 |   130 |   170  |  515|





Caso **2B**)

- Luego de aproximadamente 8 minutos de comenzar el test de carga el tiempo de respuesta comienza a subir para no bajar (siendo en ese punto  24.7 ms por request el tiempo de respuesta sobre  13350.1 transacciones)

- Siendo un minuto antes el punto pico de request con 13850.7 transacciones.

- Sin embargo durante esta prueba se observa el pico de tiempo de respuesta al minuto de comenzarla con 634 ms  siendo 16.7 las transacciones procesadas en ese momento, sin tener aún transacciones encoladas.

- Total 109146 transacciones.  

- Máximo uso de memoria y cpu de la imagen de docker: 1,0 CPU y 27 MB 

- Resumen de request:

|Name   |# reqs |# fails|Avg |Min  |Max  |Median   |req/s |
|-------|-------|-------|----|-----|-----|---------|------|
|POST /api/v1/found-prices|                                      18351  |   0(0.00%)  |    67   |    5 |    877  |      47 |  50.70|
|POST /api/v1/shops |                                            24340  |   0(0.00%)   |   41 |      4  |   717  |      25 |   72.90 |
|GET /api/v1/shops?location=Berazategui |                        12128  |   0(0.00%) |     33  |     3   |  788  |      16 |   31.40|
|GET /api/v1/shops?location=Bernal|                              12156  |   0(0.00%) |     31  |     3  |   765  |      16 |  37.00 |
|GET /api/v1/shops?location=Lanus|                               12366 |    0(0.00%) |     33  |     3  |   717  |      16 |  34.70|
|-------|-------|-------|----|-----|-----|---------|------|
|Total  | 79341 | 0(0.00%) | |     |     |         |226.70|

- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
|POST /api/v1/found-prices                               |        18351|47|64|81|96|140|190|270|320|877|
|POST /api/v1/shops                                     |         24340|25|37|49|58|98|140|210|260|717|
|GET /api/v1/shops?location=Berazategui                   |       12128|16|26|37|46|84|120|180|230|788|
|GET /api/v1/shops?location=Bernal                         |      12156|16|26|35|44|78|120|180|220|765|
|GET /api/v1/shops?location=Lanus                           |     12366|16|26|37|47|86|130|180|230|717|


### **Conclusiones**

Entre ambos casos el **2A** y el **2B** encontramos que le caso **2A** tiene algunas ventaja sobre el **2B** en cuanto a rendimiento que atribuimos a que tiene que negociar al comienzo y replicar luego sobre un sólo nodo master y uno de replica y no sobre un nodo master dos y dos de replica :

- Sobre cuando comienza a subir el tiempo de respuesta, el caso **2B** es superior de todas maneras:
 -  En el caso **2A** fue a los 5 minutos 
 - En el **2B** a los 8 minutos.
  
-  Sin embargo, el tiempo de respuesta en el caso **2A** es mejor.
 -  **2A:** 8.26 ms por request el tiempo promediode respuesta sobre  5705.9 transacciones 
 - **2B:** 24.7 ms por request el tiempo promedio de respuesta sobre 13350.1 transacciones 
     - Son 15.9 ms de diferencia (Siendo de todas maneras más del doble de transacciones en el caso **2B**)  

- Durante el pico de tiempo de respuesta hay una diferencia significativa también a favor de la prueba **2A**:  
- Durante el caso **1A** son 39 ms por request, 
- Durante el caso **1B** son 634 ms ms por request
	- El caso **2A** tardó en responder durante el pico mucho menos que el **2B** teniendo más transacciones. 
	- También observamos que ambos picos se producen al principio de la corrida, cuando las imágenes de docker estan recién levantadas y todavía no se produjo ninguna interacción con la base de datos. Llegamos a la conclusión que ambos picos se pruducen cuando las intencias de base de datos Mongo "negocian" entre ellas para saber cual de las replicas va a ser master y cual o cuales slaves. Eso explicaría porque el tiempo de respuesta es mayor en el caso de la prueba **2B**.  

- Con respecto al uso de CPU y Memoria que muestra que tuvo la imagen de docker en la solapa server no notamos diferencias significativas entre ambos casos.
	- Durante el caso **2A** se utiliza un 1,0 CPU y 25 MB como máximo.
	- Durante el caso **2B** se utiliza un 1,0 CPU y 27 MB  como máximo.

 
