Prueba de stress 
===================

####Comparativo de:

 - _Caso 3A: Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica, eliminando un nodo Slave durante el tope de carga._
 - _Caso 3B: Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica, eliminando un nodo Master durante el tope de carga._
 - _Caso 3C: Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica, eliminando un nodo Master durante el tope de carga y luego reintegrarlo al Cluster._


> **Nota:**
> En ambos casos se ha corrió el test con 500 usuarios concurrentes, con una rampa de aproximadamente 7 minutos y un hold-for de aproximadamente 3 minutos.

----------

Caso 3A)

- Al comenzar el test de carga, luego de apenas un minuto se observa un pico de 223 ms por request con 22.3 transacciones. Luego comienza a bajar considerablemente hasta llegar a los 7.78 ms en promedio por request siendo 5731.6 las transacciones en ese minuto para finalmente comenzar a subir al haber pasado ya 8 minutos del test de carga (ya entrado en la fase de hold-for).

- Luego de 7 minutos de comenzar el test de carga, antes de que comience a subir para no bajar, se alcanza el pico de request con 12082.7 aproximadamente transacciones en ese minuto.

- Casi llegando a los 10 minutos de comenzar el test de carga ya finalizandolo se alcanza el pico de tiempo de respuesta con 224 ms por request teniendo 6432.8 transacciones en ese minuto. El pico es por un mili segundo más alto que el pico observado al inicio.

- Total 91200 transacciones. 

- Máximo uso de memoria y cpu de la imagen de docker: 1,7 CPU y 60 MB. 

- Resumen de request:

|Name|# reqs|# fails|Avg|Min|Max|Median|req/s|
|-------|-------|-------|----|-----|-----|---------|------|
|POST /api/v1/found-prices                    |                  24000  |   0(0.00%)  |   133|       5|    1461  |      78|   63.90|
 |POST /api/v1/shops                            |                  6101  |   0(0.00%)   |   92 |      4 |   1349  |      35|   15.40|
 |GET /api/v1/shops?location=Berazategui         |                11898  |   0(0.00%)    |  71  |     3  |  1235  |      24|   35.40|
 |GET /api/v1/shops?location=Bernal               |               12087  |   0(0.00%)     | 71   |    3   | 1217  |      23|   30.60|
 |GET /api/v1/shops?location=Lanus                 |              11855  |   0(0.00%)      |71    |   3    |1241  |      24|   30.70|
|Total                                                         | 65941   |  0(0.00%)           | | | | |                          176.00|
|-------|-------|-------|----|-----|-----|---------|------|

Percentage of the requests completed within given times
|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
 |POST /api/v1/found-prices                         |              24000 |    78   | 130|    170|    200|    320 |   440|    620|    750|   1461|
 |POST /api/v1/shops                               |                6101 |    35   |  65 |   100 |   130|    260  |  390|    560 |   710 |  13491
 |GET /api/v1/shops?location=Berazategui             |             11898 |    24   |  49  |   81  |  110 |   200   | 310 |   450  |  550  | 1235|
 |GET /api/v1/shops?location=Bernal                   |            12087 |    23  |   48   |  80   | 110  |  200  |  310  |  480   | 600 |  1217|
 |GET /api/v1/shops?location=Lanus                     |           11855 |    24 |    49    | 80    |110   | 200   | 310   | 450    |580  | 1241|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|


Caso 3B)

- Al minuto de comenzar el test de carga se observa un pequeño pico de 48.1 ms por request con 440.9 transacciones. Luego baja para finalmente comenzar a subir a los 8 minutos del test de carga (ya entrado en la fase de hold-for) con 107 ms por request teniendo alrededor de 10191.0 transacciones.

- Siendo dos minutos antes el punto pico de request con 11395.1 transacciones.

- A los 9 minutos de comenzar el test de carga se alcanza el pico de tiempo de respuesta con 1000 ms por request teniendo 8389.7 transacciones en ese minuto.

- Un minuto antes de esto, es decir, a los 8 minutos de corrido el test, es cuando se da de baja al nodo master de la base de datos. Se ve reflejado en el test al comenzar a subir el tiempo de respuesta, al luego encolar pedidos y, se observan errores.

- Total de errores: 135. La mayoría siendo _'429 Client Error: Too Many Requests'_ al ser demasiadas las transacciones encoladas.

- Total 77542 transacciones.  

- Máximo uso de memoria y cpu de la imagen de docker: 1,0 CPU y 27 MB 

- Resumen de request:

|Name|# reqs|# fails|Avg|Min|Max|Median|req/s|
|-------|-------|-------|----|-----|-----|---------|------|
 |POST /api/v1/found-prices                          |            18903    |14(0.07%)|     171    |   6|   23283  |     100   |70.40|
 |POST /api/v1/shops                                  |            4687   | 20(0.42%) |    569   |    4 |  24922  |      52   |17.70|
 |GET /api/v1/shops?location=Berazategui               |           9645  |  27(0.28%)  |   312  |     3  | 24210  |      38  | 38.50|
 |GET /api/v1/shops?location=Bernal                     |          9650 |   34(0.35%)   |  330 |      3  | 23959  |      38 |  33.50|
 |GET /api/v1/shops?location=Lanus                       |         9218|    40(0.43%)    | 303|       3  | 23885  |      37|   31.00|
|Total                                                       |   52103 |  135(0.26%)      |  ||||                                 191.10|
|-------|-------|-------|----|-----|-----|---------|------|

- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
| POST /api/v1/found-prices                            |           18903|    100  |  150 |   190 |  220 |  330|   470 |   740|  1200 | 23283|
| POST /api/v1/shops                                   |            4687 |    52  |  92 |  130 |  160 |  290|   470  |18000| 21000 | 24922|
| GET /api/v1/shops?location=Berazategui               |            9645  |   38  |  74 |  110 |  130 |  220|   360   | 720| 17000 | 24210|
| GET /api/v1/shops?location=Bernal                    |            9650   |  38  |  71 |  100 |  130 |  230|   360   | 800| 18000 | 23959|
| GET /api/v1/shops?location=Lanus                     |            9218    | 37  |  73 |  110 |  130 |  230|   360    |700 | 17000 | 23885|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|

- Errores

| # Occurences   | Url    |   Error       |                                                                                             
|----------------|------|-------------|
| 27      |           GET /api/v1/shops?location=Berazategui| "HTTPError('429 Client Error: Too Many Requests',)" |        
| 9        |          POST /api/v1/found-prices| "HTTPError('500 Server Error: Internal Server Error',)"           |       
| 34        |         GET /api/v1/shops?location=Bernal| "HTTPError('429 Client Error: Too Many Requests',)"        |      
| 20         |        POST /api/v1/shops| "HTTPError('429 Client Error: Too Many Requests',)"                        |     
| 5           |       POST /api/v1/found-prices: "HTTPError('429 Client Error| Too Many Requests',)"                  |    
| 1            |      GET /api/v1/shops?location=Lanus: "HTTPError('500 Server Error| Internal Server Error',)"        |   
| 39            |     GET /api/v1/shops?location=Lanus: "HTTPError('429 Client Error| Too Many Requests',)"            |   
|----------------|------|-----|

Caso 3C)

- Al minuto de comenzar el test de carga se observa un pequeño pico de 89.1 ms por request con 278.0 transacciones. Luego baja para finalmente comenzar a subir pasados los 8 minutos del test de carga (ya entrado en la fase de hold-for) con 45.7 ms por request teniendo alrededor de 11121.3 transacciones.

- A los 5 minutos de comenzar a correr el test se produce el pico de requests con 11365.9 transacciones.

- A los 9 minutos de comenzar el test de carga se alcanza el pico de tiempo de respuesta con 1550 ms por request teniendo 6231.2 transacciones en ese minuto.

- Un minuto antes de esto, es decir, a los 8 minutos de corrido el test, es cuando se da de baja al nodo master de la base de datos. Se ve reflejado en el test al comenzar a subir el tiempo de respuesta, al luego encolar pedidos y, se observan errores. Un minuto después se reintegra ese nodo y se observa que, en tendencia venia bajando el tiempo de respuesta se produce una pequeño cambio de inclinación en la baja, es decir, el tiempo de respuesta deja de bajar tan abruptamente pero sigue bajando.

- Total de errores: 142. La mayoría siendo _'429 Client Error: Too Many Requests'_ al ser demasiadas las transacciones encoladas.

- Total 85578 transacciones.  

- Máximo uso de memoria y cpu de la imagen de docker: 0,8 CPU y 30 MB 

- Resumen de request:

|Name|# reqs|# fails|Avg|Min|Max|Median|req/s|
|-------|-------|-------|----|-----|-----|---------|------|
 |POST /api/v1/found-prices|  21829|12(0.05%)| 247|   5|   22523  |  85|   60.70|
 |POST /api/v1/shops|  5524|23(0.41%)| 512|   4 |  24972  |  41   |14.60|
 |GET /api/v1/shops?location=Berazategui| 10942|37(0.34%)| 320|   3   |23837  |  28|   28.90|
 |GET /api/v1/shops?location=Bernal|  10942|33(0.30%)| 314|   3   |23078  |  32 |  33.40|
 |GET /api/v1/shops?location=Lanus|   10848|37(0.34%)| 314|   3   |24082  |  31  | 32.20| 
 |Total|  60085 |  142(0.24%)|  ||||  169.80|
|-------|-------|-------|----|-----|-----|---------|------|

- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|
 |POST /api/v1/found-prices|   21829| 85|160|240|310|590|930 |  1600 |  2500 | 22523|
 |POST /api/v1/shops|   5524| 41| 95|180|250|530|920  | 2300  |20000 | 24972|
 |GET /api/v1/shops?location=Berazategui|  10942| 28| 74|130|180|410|710 |  1500|   4500 | 23837|
 |GET /api/v1/shops?location=Bernal|   10942| 32| 82|140|200|420|720  | 1500 |  4400 | 23078|
 |GET /api/v1/shops?location=Lanus|10848| 31| 78|140|200|440|790  | 1600|   3100 | 24082|
|-------|-------|-----|-----|-----|------|-----|-----|-----|-----|-------|

- Errores

| # Occurences   | Url    |   Error       |                                                                                             
|----------------|------|-------------|
 |37| GET /api/v1/shops?location=Berazategui| "HTTPError('429 Client Error: Too Many Requests',)"| 
 |7|  POST /api/v1/found-prices| "HTTPError('500 Server Error: Internal Server Error',)"|  
 |33| GET /api/v1/shops?location=Bernal| "HTTPError('429 Client Error: Too Many Requests',)"|  
 |23| POST /api/v1/shops| "HTTPError('429 Client Error: Too Many Requests',)"| 
 |5|  POST /api/v1/found-prices| "HTTPError('429 Client Error: Too Many Requests',)"|  
 |1|  GET /api/v1/shops?location=Lanus| "HTTPError('500 Server Error: Internal Server Error',)"|   
| 36| GET /api/v1/shops?location=Lanus| "HTTPError('429 Client Error: Too Many Requests',)"|      
|----------------|------|-----|

### **Conclusiones**

Entre los casos **3A**,**3B** y **3C** no encontramos diferencias significativas en cuanto a redimiento pero si encontramos diferencias en el momento de sacar un nodo / agregarlo al sistema.

- Si bien en las tres pruebas se observan picos al entrar al _hod-for_, los picos de las pruebas **3B** y **3C** son mucho más grandes :1000 ms en la prueba **3B** y 1550 ms **3C** por request mientras que en la prueba **3A** el pico es de 224 ms por request.

- Nuevamente hay una diferencia entre los casos  **3B** y **3C** y **3A**: al quitar el nodo slave en el caso **3A** no se producen errores, al contrario, tanto en el caso **3B** y **3C** cuando son los nodos master los que son quitados se producen errores y un mayor encolamiento de request porque la aplicación no puede guardar en la base de datos hasta que otro nodo slave no se vuelva master.

- Estos errores podrían mitigarse de la siguientes maneras:
	- Utilizar una cache de consulta de  pedidos que si tiene el valor requerido guardado y no expiro lo retorne (por lo cual no debería consultar a la base).
	- Utilizar una cache de consulta de  pedidos que si tiene el valor requerido guardado y expiró al no poder comunicarse con la base, lo utilice de todas maneras si el tiempo de expiración no es mayor a otro fijado (si el valor es demasiado 'viejo', no).
	- Encolar en una cola especial sólo los pedidos que no entren dentro de los dos anteriores o los 'POST' a las urls para procesarlos cuando la base de datos este nuevamente activa, calculamos por lo que hemos visto en los test que lleva algunos segundos.


 
