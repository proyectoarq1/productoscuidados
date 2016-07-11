Pruebas de stress
===================

####Comparativo de:

 A) _Caso 3A. Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica (2), eliminando un nodo Slave durante el tope de carga._

 B) _Caso 3B. Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica (2), eliminando un nodo Master durante el tope de carga._

 C) _Caso 3C. Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica (2), eliminando un nodo Master durante el tope de carga y luego reintegrarlo al Cluster._


> **Nota:**
> El test tiene las siguientes carácterísticas:
> - Rampa: 10 minutos.
> - Hold-for: 5 minutos
> - Concurrencia máxima: 500.
>  En ambos casos se ha corrido el test primero en un escenario normal y luego otro con caché request.

----------

A) Sin cache:

- Luego de aproximadamente 9 minutos de comenzar el test de carga el tiempo de respuesta comienza a subir considerablemente (siendo en ese punto 10.4 ms por request el tiempo de respuesta sobre 11035.8 transacciones)

- Siendo ese mismo el punto pico de cantidad de request

- Ya durante el tiempo de rampa (pasados los diez minutos) se observa un pico de tiempo de respuesta de 116 ms por request, siendo las transacciones 1208.7 procesadas en ese minuto. 

- Total 126758 transacciones.  

- Resumen de request:

| Name | # reqs | # fails | Avg | Min |  Max  | Median | req/s |
|------|--------|---------|-----|-----|-------|--------|-------|
|POST /api/v1/found-prices| 27966 | 0(0.00%)| 94 | 4 | 738 | 72 | 67.50|
|POST /api/v1/shops | 7001 |0(0.00%)| 38 | 3 |528 | 18|  18.90 |
|GET /api/v1/shops?location=Berazategui|13885|0(0.00%)|32|2|613| 14|30.70|
|GET /api/v1/shops?location=Bernal|13930|0(0.00%)|33|2|619|14|32.30|
|GET /api/v1/shops?location=Lanus| 14137|0(0.00%)|33|2|565|14|33.40|
|-------|------|--------|------|-----|-------|--------|--------|
|Total  |76919 |0(0.00%)|      |     |       |        |182.80  |
 
- Percentage of the requests completed within given times

|Name|# reqs|50%|66%|75%|80%|90%|95%|98%|99%|100%|
|-------|-------|------|-----|-----|-----|-----|-----|-----|-----|--------|
|POST /api/v1/found-prices|27966|72|110|130|150|200|250|310|360|738|
|POST /api/v1/shops|7001|18|31|44|55|97|150|210|260|528|
|GET /api/v1/shops?location=Berazategui|13885|14|26|38|48|83|130|200|250|613|
|GET /api/v1/shops?location=Bernal|13930|14|26|39|49|86|140|210|260|619|
|GET /api/v1/shops?location=Lanus|14137|14|26|38|48|86|130|200|250|565|


A) Con cache: 

- Al comiezo del test se vé un pico de 168 ms por request sobre 21.5 transacciones. Se cree que este retraso se debe al estado inicial de la caché.
- Al siguiente minuto los tiempos mejoran abruptamente.
- Finalizando el período de rampa se visualiza un tiempo de 6.02 ms por request sobre 10397.3 transacciones.

- Ya pasado el tiempo de rampa, durante el hold-for del test se vé un pico de 28.9 ms por request sobre 2855.8 transacciones.

- El pico de request es de 12047.4 con 16.8 ms por request.
- Total 135295 transacciones. 
- Resumen de requests:

| Name  |  # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-------|----------|-------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices | 28608  |   0(0.00%)  |    43   |    3  |   387  |      31 |  69.20 |
| POST /api/v1/shops  | 7107  |   0(0.00%)  |    13   |    3  |   293  |     7 |  16.80 |
| GET /api/v1/shops?location=Berazategui | 14265  |   0(0.00%)  |     9   |    1 |     274  |       4  | 34.80 |
| GET /api/v1/shops?location=Bernal  |  14526  |   0(0.00%)  |     9  |     1  |   303  |       4 |  34.60 |
| GET /api/v1/shops?location=Lanus  |  14214  |   0(0.00%)   |    9  |     1 |     983  |       4 |  36.80 |
|------|----------|--------------|-------|--------|-------|----------|---------|
| Total|   78720  |   0(0.00%)   |       |        |       |          | 192.20  |

- Percentage of the requests completed within given times

| Name  |  # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98% |   99% |  100% |
|-------|----------|-------|-------|-------|-------|-------|-------|------|-------|-------|
| POST /api/v1/found-prices | 28608  |   31   |  42  |   54  |   64  |   91  |  120  |  160  |  190  |  387 |
| POST /api/v1/shops  | 7107  |    7  |   11   |  15  |   18  |   29  |   44 |     69  |   99  |  293 |
| GET /api/v1/shops?location=Berazategui | 14265  |    4   |   7  |   10  |   12 |     22  |   34  |   56  |   79  |  274 |
| GET /api/v1/shops?location=Bernal | 14526  |    4  |    7  |   10  |   12  |   22  |   35  |   57  |   81  |  303 |
| GET /api/v1/shops?location=Lanus  |  14214  |    4 |     7  |   10  |   12 |    21  |   34  |   57  |   77  |  983 |


------
B) Sin cache: 

- Los tiempos de respuesta se mantienen con subas debido a la rampa pero mínimas.

- Un minuto antes de quitar el nodo master de mongo se visualizan tiempos de 11.7 ms por request sobre 10153.1 transacciones.

- Al bajar el nodo master se producen 2 request fallidos lo que genera un pico de 653 ms por request sobre 10805.2 transacciones.
- Al siguiente minuto un nodo secundario pasa a ser master y se visualiza un tiempo de 22.8 ms por request sobre 11858.7 transacciones
- El pico de request es el nombrado anteriormente
- Finalizando el test, durante el hold-on se vé un último pico de 121 ms por request sobre 1793.8 transacciones. 
- Total 123386 transacciones.
- Resumen de requests:

| Name  |  # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-------|----------|-------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices | 26772  |   1(0.00%)  |    87   |    3 |   3635  |      46 |  68.10 |
| POST /api/v1/shops | 6748  |   0(0.00%)  |   429  |     3 |  23568  |      10 |   18.20 |
| GET /api/v1/shops?location=Berazategui  | 13146  |   1(0.01%)  |   214    |   2 |   23561  |       7 |  30.50 |
| GET /api/v1/shops?location=Bernal | 13484  |   0(0.00%)  |   230  |     2 |   23204  |       7 |  36.00 |
| GET /api/v1/shops?location=Lanus  |  13430  |   0(0.00%)  |   226   |    2 |  23546  |       7 |  34.60 |
|------|---------|--------------|--------|-------|--------|---------|--------|
| Total|  73580  |   2(0.00%)   |        |       |        |         |  187.40|

- Percentage of the requests completed within given times

| Name | # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98% |   99% |  100% |
|------|---------|-------|-------|-------|-------|-------|-------|------|-------|------|
| POST /api/v1/found-prices  | 26772  |   46  |   72  |   92  |  110  |  160 |   220  |  420  |  850  | 3635 |
| POST /api/v1/shops  | 6748  |   10  |   17  |   24  |   31  |   64  |  150 |  1300 | 21000 | 23568 |
| GET /api/v1/shops?location=Berazategui | 13146   |   7  |   14  |   21  |   28 |     55  |  120  |  510 |  2600 | 23561 |
| GET /api/v1/shops?location=Bernal | 13484   |   7  |   14  |   21  |   28  |   56  |  130  |  550 |  3100 | 23204 |
| GET /api/v1/shops?location=Lanus  |  13430  |    7   |  14  |   22  |   28  |   57  |  130  |  610  | 2700 | 23546 |

- Error report

| # occurences  |  Error |                                                                                               
|---------------|--------|
| 1  |  GET /api/v1/shops?location=Berazategui: "HTTPError('500 Server Error: Internal Server Error',)" |
| 1   |   POST /api/v1/found-prices: "HTTPError('500 Server Error: Internal Server Error',)"  |

B) Con cache: 

- Los tiempos de respuesta se mantienen con subas debido a la rampa pero mínimas.

- Un minuto antes de quitar el nodo master de mongo se visualizan tiempos de 8.01 ms por request sobre 11029.2 transacciones.

- Al bajar el nodo master se producen 2 request fallidos lo que genera un pico de 880 ms por request sobre 10323.6 transacciones.
- Al siguiente minuto un nodo secundario pasa a ser master y se visualiza un tiempo de 17.4 ms por request sobre 12039.8 transacciones
- El pico de request es el nombrado anteriormente.
- Finalizando el test, durante el hold-on se vé un último pico de 95.8 ms por request sobre 1168.5 transacciones. 
- Total 127029 transacciones.
- Resumen de requests:

| Name  |  # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-------|----------|-------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices |  26757  |   1(0.00%)  |    82  |     3 |  22400  |      37  | 67.50 |
| POST /api/v1/shops | 6666  |   1(0.01%)  |   404  |     3 |  22158  |       8 |   15.50 |
| GET /api/v1/shops?location=Berazategui | 13254  |   0(0.00%)  |   216  |     1 |  22106  |       5 |  35.80 |
| GET /api/v1/shops?location=Bernal | 13416  |   0(0.00%)  |   194  |     1  | 21941  |       5  | 33.70 |
| GET /api/v1/shops?location=Lanus  | 13530  |   0(0.00%)  |   199  |     1 |  22076  |       5 |  36.00 |
|------|----------|-------------|--------|---------|------|---------|---------|
| Total|   73623  |   2(0.00%)  |        |         |      |         |  188.50 |

- Percentage of the requests completed within given times

| Name | # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98%  |  99%  | 100% |
|------|---------|-------|-------|-------|-------|-------|-------|-------|-------|----|
| POST /api/v1/found-prices  | 26757  |   37  |   55  |   74   |  87  |  130 |   190  |  350 |  1000 | 22400 |
| POST /api/v1/shops  | 6666  |    8  |   12  |   18  |   22  |   48  |   99 |   500 | 20000 | 22158 |
| GET /api/v1/shops?location=Berazategui | 13254  |    5   |   9  |   14  |   18 |    40  |   89  |  460 |  2500 | 22106 |
| GET /api/v1/shops?location=Bernal | 13416  |    5   |   9  |   13  |   17   |  39  |   86  |  260 |  2300 | 21941 |
| GET /api/v1/shops?location=Lanus  | 13530   |   5  |    9  |   14  |   18  |   39  |   85  |  310 |  2400 | 22076 |

- Error report

| # occurences   |    Error  |                                                                                             
|----------------|-----------|
| 1              |  POST /api/v1/shops: "HTTPError('500 Server Error: Internal Server Error',)" |                        
| 1              |   POST /api/v1/found-prices: "HTTPError('500 Server Error: Internal Server Error',)" |                 


------
C) Sin cache: 

- Los tiempos de respuesta se mantienen con subas debido a la rampa pero mínimas.

- Un minuto antes de quitar el nodo master de mongo se visualizan tiempos de 7.72 ms por request sobre 10495 transacciones.

- Al bajar el nodo master se producen 4 request fallidos lo que genera un pico de 592 ms por request sobre 9279.7 transacciones.
- Al siguiente minuto un nodo secundario pasa a ser master y se reintegra el nodo bajado anteriormente, visualizándose un tiempo de 69.1 ms por request sobre 11464.8 transacciones
- El pico de request es el nombrado anteriormente
- Finalizando el test, durante el hold-for se vé un último pico de 58.6 ms por request sobre 4271.4 transacciones. 
- Total 123360 transacciones.
- Resumen de requests:

| Name  |  # reqs  |    # fails |    Avg  |   Min  |   Max  |  Median |  req/s |
|-------|----------|------------|---------|--------|--------|---------|--------|
| POST /api/v1/found-prices  | 26784  |   3(0.01%) |     95    |   3  |  3430  |      46  | 71.50 |
| POST /api/v1/shops  | 6834  |   1(0.01%)  |   375  |     3 |  22778  |       9  | 19.40 |
| GET /api/v1/shops?location=Berazategui | 13188  |   0(0.00%)  |   230  |     2 |  22787  |       7 |  35.70 |
| GET /api/v1/shops?location=Bernal | 13324  |   0(0.00%)  |   193  |     2 |  22663  |       7 |  35.50 |
| GET /api/v1/shops?location=Lanus  | 13398  |   0(0.00%)  |   239   |    2  | 22740  |       7 |  31.70 |
|------|---------|------------|---------|---------|--------|--------|---------|
| Total|  73528  |   4(0.01%) |         |         |        |        |  193.80 |

- Percentage of the requests completed within given times

| Name | # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98% |   99%  | 100% |
|------|---------|-------|-------|-------|-------|-------|-------|------|--------|----|
| POST /api/v1/found-prices | 26784  |   46  |   71  |   91 |   110  |  160  |  220  |  460 |  1400 |  3430 |
| POST /api/v1/shops | 6834  |    9  |   15  |   22   |  28  |   62  |  130  |  710 | 19000 | 22778 |
| GET /api/v1/shops?location=Berazategui | 13188  |    7  |   12  |   18  |   25 |    58  |  130  |  560 | 15000 | 22787 |
| GET /api/v1/shops?location=Bernal | 13324   |   7  |   12   |  19  |   26  |   60  |  130  |  380 |  2300 | 22663 |
| GET /api/v1/shops?location=Lanus  | 13398  |    7  |   12  |   18  |   26 |    61 |   140  |  750 | 15000 | 22740 |

- Error report

| # occurences   |    Error |                                                                                              
|----------------|----------|
| 1              |  POST /api/v1/shops: "HTTPError('500 Server Error: Internal Server Error',)" |                        
| 3              |  POST /api/v1/found-prices: "HTTPError('500 Server Error: Internal Server Error',)" |

C) Con cache: 

- Los tiempos de respuesta se mantienen con subas debido a la rampa pero mínimas.

- Un minuto antes de quitar el nodo master de mongo se visualizan tiempos de 8.11 ms por request sobre 9453.7 transacciones.

- Al bajar el nodo master se producen 4 request fallidos lo que genera un pico de 891 ms por request sobre 8048.8 transacciones.
- Al siguiente minuto un nodo secundario pasa a ser master y se reintegra el nodo bajado anteriormente, visualizándose un tiempo de 300 ms por request sobre 13160.8 transacciones
- El pico de request es el nombrado anteriormente.
- Finalizando el test, durante el hold-on se vé un último pico de 31.4 ms por request sobre 8560.8 transacciones. 
- Total 130582 transacciones.
- Resumen de requests:

| Name  |  # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-------|----------|-------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices  | 26909  |   1(0.00%)  |    68   |    3 |  22118  |      32 |  69.10 |
| POST /api/v1/shops   | 6694  |   3(0.04%) |    368  |     3 |  22749  |       7 |  16.50 |
| GET /api/v1/shops?location=Berazategui | 13626  |   0(0.00%)  |   200   |    1 |  22801  |       4  | 35.40 |
| GET /api/v1/shops?location=Bernal | 13456  |   0(0.00%)  |   205   |    1 |  22852  |       4 |  37.30 |
| GET /api/v1/shops?location=Lanus   | 13362  |   0(0.00%)  |   206   |    1  | 22831  |       4 |  33.80 |
|------|---------|------------|---------|--------|--------|---------|---------|
| Total    74047 |    4(0.01%)|         |        |        |         |  192.10 |

- Percentage of the requests completed within given times

| Name  | # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98%  |  99% |  100% |
|-------|---------|-------|-------|-------|-------|-------|-------|-------|------|----|
| POST /api/v1/found-prices  | 26909  |   32  |   43  |   55   |  65  |   98 |   140  |  240  |  840 | 22118 |
| POST /api/v1/shops | 6694  |    7 |    10  |   14  |   16  |   29  |   51  |  190 | 20000 | 22749 |
| GET /api/v1/shops?location=Berazategui  | 13626  |    4   |   7   |   9  |   12 |    22  |   42  |  150 |  2600 | 22801 |
| GET /api/v1/shops?location=Bernal  | 13456   |   4   |   7   |   9  |   11  |   21  |   46  |  190 |  2500 | 22852 |
| GET /api/v1/shops?location=Lanus  | 13362   |   4   |   7  |    9  |   11  |   22 |    45 |   180 |  3000 | 22831 |

- Error report

| # occurences   |    Error  |                                                                                             
|----------------|-----------|
| 3              |  POST /api/v1/shops: "HTTPError('500 Server Error: Internal Server Error',)" |                        
| 1              |  POST /api/v1/found-prices: "HTTPError('500 Server Error: Internal Server Error',)"  |


----

### **Conclusión**

Lo más significativo entre estos 3 casos es la diferencia entre bajar un nodo slave y un master(caso A y B). Pasando el primer caso prácticamente desapercibido por las estadísticas mientras que en el segundo caso la aplicación sufre un pico de espera abrupto encolando gran cantidad de requests. Situación que es remediada inmediatamente un slave se vuelve master.
El caso en el que se reintegra el nodo primeramente bajado(caso A) no pareciera tener efecto en la aplicación dado que los resultados son muy similares a los del caso en el que no se reintegra el nodo bajado.