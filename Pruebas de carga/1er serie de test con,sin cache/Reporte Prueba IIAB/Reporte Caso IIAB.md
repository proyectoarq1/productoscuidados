Pruebas de stress
===================

####Comparativo de:
A. _Caso 2A. Desempeño de un nodo virtual de aplicación con 1 CPU; y 2 nodos de BD en réplica._
B. _Caso 2B. Desempeño de un nodo virtual de aplicación con 1 CPU; y 3 nodos de BD en réplica._

> **Nota:**
> El test tiene las siguientes carácterísticas:
> - Rampa: 10 minutos.
> - Hold-for: 5 minutos
>  - Concurrencia máxima: 500.
>  En ambos casos se ha corrido el test primero en un escenario normal y luego otro con caché request.

----------

A) Sin cache: 

- Durante el período de rampa el tiempo se mantiene por debajo de los 20 ms.

- Al minuto de superado ese período el tiempo de respuesta empieza a subir considerablemente(en ese punto había un tiempo de 14 ms por request sobre 10080.4 transacciones)

- Durante el hold-for del test y finalizando el test se vé un pico de 48.2 ms por request sobre 319.7 transacciones.

- El pico de request es de 12644.3 con 11.2 ms por request.

- Total 128361 transacciones.  

- Resumen de request

| Name      |         # reqs   |   # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-----------|----------------|-----------|--------|--------|---------|-----------|--------|
| POST /api/v1/found-prices | 28468  |   0(0.00%) |     55   |    3   |  710  |      37  | 70.10 |
| POST /api/v1/shops        |  7034  |   0(0.00%) |     17   |    3   |  343  |       8  | 16.80 |
| GET /api/v1/shops?location=Berazategui |  14107 |  0(0.00%) |    14 |    2  |   510  |       5 |  34.80 |
| GET /api/v1/shops?location=Bernal      |  14296 |  0(0.00%) |    14 |    2  |   425  |       6 |  35.80 |
| GET /api/v1/shops?location=Lanus  |   14486 |    0(0.00%)  |    14  |     2 |    411  |       6 |  36.00 |
|---------|-------------|----------|----------|-----|-------|-------|-------------|
| Total   |       78391 | 0(0.00%) |          |     |       |       |        193.50

- Percentage of the requests completed within given times
| Name          |                         # reqs  |  50%  |  66% |   75% |   80% |   90%  |  95% |   98% |   99% |  100% |
|---------------|---------------------------|--------|--------|--------|--------|---------|-------|--------|--------|----------|
| POST /api/v1/found-prices | 28468 |  37  |   54  |   70  |   83  |  120  |  160 |   210 |   250  |  710 |
| POST /api/v1/shops | 7034 |   8  |   13  |   18  |   22  |   38  |   61  |  110 |   160 |   343 |
| GET /api/v1/shops?location=Berazategui |  14107  |    5  |    9  |   13  |   17 |    32 |    53  |   95  |  150  |  510 |
| GET /api/v1/shops?location=Bernal |  14296  |    6  |   10  |   14   |  17  |   32  |   52  |   97  |  150 |   425 |
| GET /api/v1/shops?location=Lanus  | 14486   |   6   |   9  |   13  |   17  |   32  |   53  |   95  |  150  |  411 |


------
A) Con cache: 

- Al comienzo del test de carga se vé un pico de 15.4 ms sobre 442.4 transacciones, se cree que este pico es por el estado inicial de la caché. Luego el tiempo baja.

- Finalizando el período de rampa se visualiza el comienzo de una suba progresiva considerable del tiempo de respuesta. Siendo en este punto 4.37 ms por request sobre 9900 transacciones. 

- Ya pasado el tiempo de rampa, durante el hold-for del test se vé un pico de 35.4 ms por request sobre 10307,5 transacciones.

- El pico de request fue de 12722 con 11.8 ms por request.
- Total 139727 transacciones. 
- Resumen de requests
| Name      |  # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-----------|----------|------------|--------|--------|---------|---------|--------|
| POST /api/v1/found-prices  | 30191  | 0(0.00%)  |  49 |  3  |   943  |   33 |  70.10 |
| POST /api/v1/shops         | 7623   |  0(0.00%) |  16 |  3  |   443  |   8  | 18.30 |
| GET /api/v1/shops?location=Berazategui  | 15067 |  0(0.00%) |   12   |    1 |    543  |       5 |  33.60 |
| GET /api/v1/shops?location=Bernal  | 14900  |   0(0.00%)  |    12    |   1  |   412  |       5 |  34.40 |
| GET /api/v1/shops?location=Lanus  |   15174  |   0(0.00%)  |    12  |     1  |   426  |       5 |  35.60 |
|----------|----------|-------------|--------|---------|--------|----------|--------|
|Total     |   82955  |   0(0.00%)  |        |         |        |          | 192.00 |

- Percentage of the requests completed within given times

| Name     |  # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98%  |  99%  | 100% |
|----------|----------|-------|-------|------|--------|------|--------|------|-------|---------|
| POST /api/v1/found-prices  | 30191  |   33  |   48  |   63  |   74  |  110 |   150 |   190  |  230  |  943 |
| POST /api/v1/shops | 7623   |   8  |   12  |   17  |   20  |   35   |  57  |  110  |  150 |   443 |
| GET /api/v1/shops?location=Berazategui | 15067  |    5   |   8  |   11  |   15  |   28 |    47  |   84  |  130  |  543 |
| GET /api/v1/shops?location=Bernal | 14900  |    5   |   8  |   12  |   14  |   27   |  45  |   79  |  120  |  412 |
| GET /api/v1/shops?location=Lanus |  15174  |    5   |   8  |   12  |   15  |   28  |   47 |     83 |   140 |   426 


------
B) Sin cache:  

- El tiempo de respuesta tiene subas menores durante el período de rampa. Al siguiente minuto de terminar ese período se puede ver una tendencia creciente considerable. Teniendo a ese punto 12.9 ms por request sobre un total de 11710.3 transacciones.

- Llegando al final del test se vé un pico de 54 ms por request sobre 349.5 transacciones. 

- El pico de request fue constante desde finalizada la rampa y durante los últimos minutos antes del pico anteriormente mencionado. Teniendo 11.7k transacciones promedio por minuto.
- Total 130201 transacciones.

- Resumen de request

| Name   |   # reqs  |    # fails |    Avg  |   Min  |   Max  |  Median |  req/s |
|---------|-----------|-----------|---------|--------|---------|-----------|--------|
| POST /api/v1/found-prices | 29174 |    0(0.00%)  |    51   |    3  |   433  |      36 |   69.50 |
| POST /api/v1/shops  | 7278  |   0(0.00%)  |    13   |    3  |   258  |       7 |  17.40 |
| GET /api/v1/shops?location=Berazategui  | 14466  |   0(0.00%)  |    11   |    2   |  262 |       5 |  32.10 |
| GET /api/v1/shops?location=Bernal | 14619  |   0(0.00%)   |   11   |    2  |   384  |       5  | 33.20 |
| GET /api/v1/shops?location=Lanus    | 14720  |   0(0.00%)   |   11   |    2  |   274  |       5 |  38.70 |
|--------|-----------|------------|---------|--------|--------|-------------|--------|
| Total  |    80257  |   0(0.00%) |         |        |        |             |  190.90 |

- Percentage of the requests completed within given times
 
| Name   |  # reqs |   50%  |  66% |   75%  |  80%  |  90%  |  95%   | 98%  |  99%  | 100% |
|--------|--------|---------|------|-------|--------|------|--------|-------|------|--------|
| POST /api/v1/found-prices | 29174  |   36  |   52  |   67  |   78  |  110  |  140  |  180  |  200 |   433 |
| POST /api/v1/shops | 7278  |    7   |  11  |   15  |   17  |   30  |   46  |   73  |   95  |  258 |
| GET /api/v1/shops?location=Berazategui | 14466   |   5   |   8  |   11  |   14   |  25 |     41  |   66  |   91  |  262 |
| GET /api/v1/shops?location=Bernal | 14619   |   5   |   8  |   11  |   14  |   25  |   42  |   67  |   92 |   384 |
| GET /api/v1/shops?location=Lanus   | 14720   |   5   |   8  |   12  |   14  |   25  |   43 |     68  |   90 |   274 |



B) Con cache: 

- Al comenzar el test de carga, al minuto se visualiza un pico con 103 ms sobre 11.8 transacciones. Al siguiente minuto este pico baja considerablemente, y al 3 minuto se ven 4.51 ms sobre 1842.7 transacciones.
Se cree que este tiempo es por el estado inicial de la caché

- Durante el período de rampa el tiempo de respuesta se mantiene por debajo de los 20 ms.

- Al finalizar el período de rampa se puede el comienzo de una creciente en los tiempos. Teniendo en este punto 8.63 ms sobre 12417.5 transacciones. 

- Luego de pasar ese período, durante el hold-for se puede ver un pico de 35.1 ms sobre 9211 transacciones.

- El pico de request es de 13946.2 con 46.2 ms por request.
- Total 134656 transacciones.
- Resumen de requests

| Name   |  # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|--------|----------|-------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices  | 29570  |   0(0.00%)  |    49  |     3  |   421  |      34 |   71.00 |
| POST /api/v1/shops  | 7408  |   0(0.00%)  |    13  |     3  |   339  |       7 |  20.00 |
| GET /api/v1/shops?location=Berazategui |  14792  |   0(0.00%)  |    10   |    1  |   237 |       5 |  33.40 |
| GET /api/v1/shops?location=Bernal  | 14366  |   0(0.00%)   |    9    |   1  |   344  |       5 |  32.20 |
| GET /api/v1/shops?location=Lanus  |  15170  |   0(0.00%)  |    10   |    1  |   283  |       4 |  35.40 |
|--------|---------|------------|--------|--------|--------|----------|---------|
| Total  |  81306  |   0(0.00%) |        |        |       |           |  192.00 |

- Percentage of the requests completed within given times
 
| Name  |  # reqs |   50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98%  |  99% |   100% |
|-------|---------|--------|-------|-------|-------|-------|-------|-------|------|------|
| POST /api/v1/found-prices  | 29570  |   34  |   48  |   63  |   74  |  110 |   140 |    180  |  200  |  421 |
| POST /api/v1/shops | 7408  |    7   |  11  |   14  |   17  |   28  |   43   |  69  |   92  |  339 |
| GET /api/v1/shops?location=Berazategui | 14792  |    5  |    7  |   10  |   13  |   23 |     37  |   58  |   79  |  237 |
| GET /api/v1/shops?location=Bernal  | 14366  |    5   |   7   |  10  |   13  |   22  |    36  |   58  |   80  |  344 |
| GET /api/v1/shops?location=Lanus  |  15170  |    4   |   7  |   10  |   13  |   23  |   36 |     59  |   82  |  283 |


----

### **Conclusión**

Entre estos casos no hay una diferencia considerable. Son bastante similares tanto los tiempos de respuesta como las cantidad de request y los picos informados. Lo cual era esperable puesto que la única diferencia es una replica de mongo más.


