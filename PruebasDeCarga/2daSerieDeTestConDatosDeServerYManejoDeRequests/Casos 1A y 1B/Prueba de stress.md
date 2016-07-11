Prueba de stress 
===================

####Comparativo de:

 - _Caso **1A**: Desempeño de un nodo virtual de aplicación con 1 CPU; y 1 nodo de BD_
 - _Caso **1B**: Desempeño de un nodo virtual de aplicación con 2 CPU; y 1 nodo de BD_


> **Nota:**
> En ambos casos se ha corrió el test con 500 usuarios concurrentes, con una rampa de aproximadamente 7 minutos y un hold-for de aproximadamente 3 minutos.

----------

Caso **1A**)

- Luego de aproximadamente 7 minutos de comenzar el test de carga el tiempo de respuesta comienza a subir considerablemente (siendo en ese punto  42.7 ms por request el tiempo de respuesta sobre  11466.4 transacciones)

- Habiendo ya pasado anteriormente por el punto pico de request con 11782.3 transacciones tres minutos antes.

- Ya durante el tiempo de hold-for (pasados los siete minutos, casi al cumplirse los 10) se observa un pico de tiempo de respuesta de 189 ms por request, siendo las transacciones 523.4 procesadas en ese minuto, teniendo transacciones encoladas.

- Total 92491 transacciones. 

- Máximo uso de memoria y cpu de la imagen de docker: 1,7 CPU y 51 MB 

- Resumen de request:

|Name|# reqs|# fails|Avg|Min|Max|Median|req/s|
|-------|-------|-------|----|-----|-----|---------|------|
|POST /api/v1/found-prices|24455|0(0.00%)|77|5|858|51|63.90|
|POST /api/v1/shops|6297|0(0.00%)|42|4|767|21|15.80|
|GET /api/v1/shops?location=Berazategui|12293|0(0.00%)|33|3|680|14|33.10|
|GET /api/v1/shops?location=Bernal|12015|0(0.00%)|33|3|618|14|34.80|
|GET /api/v1/shops?location=Lanus|12066|0(0.00%)|32|3| 699|14 |32.20|
|-------|-------|-------|----|-----|-----|---------|------|
|Total  |67126  |0(0.00%)|   |     |     |         |179.80|

- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
|POST /api/v1/found-prices|24455|51|78|99|110|160|220| 340|420|858|
|POST /api/v1/shops|6297|21|34|46|57|94|150|250|340 |767|
|GET /api/v1/shops?location=Berazategui|12293|14|23|  34|44|83|130|220|310|680|
|GET /api/v1/shops?location=Bernal|12015|14|24|33|42|    80|130|220|300|618|
|GET /api/v1/shops?location=Lanus|12066|14|     23|33|41|77|120|200|300|699|


Caso **1B**)

- Luego de aproximadamente 8 minutos de comenzar el test de carga el tiempo de respuesta comienza a subir para no bajar (siendo en ese punto  30.4 ms por request el tiempo de respuesta sobre  11964.5 transacciones)

- Siendo ese mismo ese el punto pico de request.

- Ya durante el tiempo de hold-for (pasados los diez minutos, casi al cumplirse los 12) se observa un pico de tiempo de respuesta de 77.8 ms por request, siendo las transacciones 2204.5 procesadas en ese minuto, teniendo transacciones encoladas.

- Total 93030 transacciones.  

- Máximo uso de memoria y cpu de la imagen de docker: 0,9 CPU y 28 MB 

- Resumen de request:

|Name   |# reqs |# fails|Avg |Min  |Max  |Median   |req/s|
|-------|-------|-------|----|-----|-----|---------|------|
|POST /api/v1/found-prices |  24703   |  0(0.00%)   |   73    |   4  |   716  |      50  | 67.80 |
|POST /api/v1/shops |  6217  |   0(0.00%)   |   35   |    4   |  593  |      17 |  17.20 |
| GET /api/v1/shops?location=Berazategui |  12296  |   0(0.00%)  |    30  |     3  |   539  |      13 |  34.20|
| GET /api/v1/shops?location=Bernal | 12284   |  0(0.00%) |     29   |    3  |   529  |      13  | 35.90 |
|GET /api/v1/shops?location=Lanus   | 12397  |   0(0.00%)  |    29  |     3  |   530  |      13 |  33.80|

- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
|POST /api/v1/found-prices   |                                    24703  |   50  |   77   |  98  |  110 |   160  |  210 |   280  |  350  |  716 |
|POST /api/v1/shops |                                              6217 |    17  |   27  |   38   |  48  |   82  |  130 |   210 |   300 |   593 |
| GET /api/v1/shops?location=Berazategui |                         12296 |    13  |   22  |   32 |    40  |   75  |  120 |   180 |   250  |  539 |
|GET /api/v1/shops?location=Bernal |                              12284   |  13  |   21 |    30 |    38  |   70 |   110 |   170  |  230  |  529 |
|GET /api/v1/shops?location=Lanus |                               12397  |   13  |   22  |   31  |   39  |   74  |  120  |  180  |  230 |   530|


### **Conclusiones**

El CPU extra utilizado permitió que en el segundo caso (Caso **1B**) se dieran las siguientes situaciones favorables:

- El tiempo de respuesta comienza a subir después:
 -  En el caso **1A** fue a los 7 minutos 
 - En el **1B** a los 8 minutos.
  
-  Además, en ese punto el tiempo de respuesta en el caso **1B** era también mejor.
 -  **1B:** 30.4 ms por request el tiempo promediode respuesta sobre  11964.5 transacciones 
 - **1A:** 42.7 ms por request el tiempo promediode respuesta sobre 11466.4 transacciones 
     - Son 12.3 ms de diferencia (Siendo 502.1 transacciones menos en el caso **1A**)  

- Durante el pico de tiempo de respuesta hay una diferencia significativa también a favor de la prueba **1B**: 
- Durante el caso **1A** son 189 ms por request, 
- Durante el caso **1B** son 77.8 ms por request
	- El caso **1A** tardó en responder durante el pico más del doble que el **1B**.

- Con respecto al uso de CPU y Memoria que muestra que tuvo la imagen de docker en la solapar server también hay diferencias notables a favor de la corrida **1B**
	- Durante el caso **1A** se utiliza un 1,7 CPU y 51 MB como máximo.
	- Durante el caso **1B** se utiliza un 0,9 CPU y 28 MB como máximo.
		- La diferencia entre la memoria y la CPU utilizada es un como menor a la mitad, siendo la corrida **1B** la que consume menos recursos. 
