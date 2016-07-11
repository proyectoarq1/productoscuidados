Pruebas de stress
===================

####Comparativo de:

1. _Caso 1A. Desempeño de un nodo virtual de aplicación con 1 CPU; y 1 nodo de BD._

2. _Caso 1B Desempeño de un nodo virtual de aplicación con 2 CPU; y 1 nodo de BD._


> **Nota:**
> El test tiene las siguientes carácterísticas:
> - Rampa: 10 minutos.
> - Hold-for: 5 minutos
> - Concurrencia máxima: 500.
>  En ambos casos se ha corrido el test primero en un escenario normal y luego otro con caché request.

----------

1) Sin cache: 

- Luego de 4 minutos comenzado el test de carga el tiempo de respuesta empieza a subir considerablemente(en ese punto había un tiempo de 583 ms por request sobre 5213.3 transacciones)

- Siendo el punto anterior el pico de cantidad de request.

- A los 7 minutos se dispara una alerta de *Alert de Apdex Score < 0.7* (la rampa alcanzaba su punto máximo a los 10 minutos).
En ese punto se vé un tiempo de 3630 ms por request. Y 4188.1 transacciones.

- Ya pasado el tiempo de rampa(10 minutos), durante el hold-for del test se vé un pico de 6200 ms por request sobre 1731.4 transacciones.

- Total 60393 transacciones.  
- Resumen de requests:

| Name |  # reqs   |   # fails  |   Avg  |   Min  |   Max  |  Median  | req/s |
|------|-----------|------------|--------|--------|--------|----------|-------|
|POST /api/v1/found-prices | 10237  |   0(0.00%)  |  5076  |   123 |  29778  |    2900 |  22.90 |
| POST /api/v1/shops |  2602  |   0(0.00%) |   4834  |    35 |  29006  |    2500  |  5.70 |
| GET /api/v1/shops?location=Berazategui | 5172  |   0(0.00%)  |  4875  |  31 |  29222  |    2700  |  9.20 |
| GET /api/v1/shops?location=Bernal | 4970  |   0(0.00%) |   4708   |   19 |  29596  |    2500  |  8.80 |
| GET /api/v1/shops?location=Lanus | 5121  |   0(0.00%)  |  4920   |   32 |   29618  |    2800 |  10.90 |
|------|----------|------------|-------|---------|--------|---------|---------|
| Total |  28102  |   0(0.00%) |       |         |        |         |  57.50  |

- Percentage of the requests completed within given times
 
| Name | # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98%  |  99%  | 100% |
|------|---------|-------|-------|-------|-------|-------|-------|-------|-----|------|
| POST /api/v1/found-prices | 10237 |  2900 |  5700 |  8500 | 10000 | 13000 | 15000 | 17000 | 21000 | 29778 |
| POST /api/v1/shops | 2602 |  2500 |  5500 |  8500 | 10000 | 12000 | 15000 | 16000 | 20000 | 29006 |
| GET /api/v1/shops?location=Berazategui | 5172 |  2700 |  5400 |  8500 | 10000  12000 | 14000 | 16000 | 21000 | 29222 |
| GET /api/v1/shops?location=Bernal | 4970 |  2500 |  5200 |  7900 |  9800 | 12000 | 14000 |  16000 | 20000 | 29596 |
| GET /api/v1/shops?location=Lanus  |  5121 |  2800 |  5600 |  8600 | 10000 | 12000 | 14000 | 17000 | 20000 | 29618 |

1) Con cache: 

- A partir del minuto 4 de comenzado el test se visualiza una creciente considerable en el tiempo de respuesta. Teniendo a este punto 81.2 ms por request sobre 3881.6 transacciones.
- A los 8 minutos se dispara una alerta de *Alert de Apdex Score < 0.7* (la rampa alcanzaba su punto máximo a los 10 minutos).
En ese punto se vé un tiempo de 1160 ms por request sobre 5202 transacciones.
- Siendo ese punto el pico de request.

- Ya pasado el tiempo de rampa, durante el hold-for del test se vé un pico de 6160 ms por request sobre 81 transacciones.

- Total 50818 transacciones. 
- Resumen de requests:

| Name  |  # reqs  |   # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|-------|----------|------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices | 9253  |   0(0.00%)  |  4410  |   130 |  42154  |    1700 |   25.80 |
| POST /api/v1/shops | 2378  |   0(0.00%) |   4220  |    45 |  42124  |    1500 |   6.00 |
| GET /api/v1/shops?location=Berazategui | 4624  |   0(0.00%) |   4108  |    21 |  41508 |  1400 |  13.00 |
| GET /api/v1/shops?location=Bernal |  4491  |   0(0.00%)  |  4401   |   29 |  41874  |    1500 |  14.70 |
| GET /api/v1/shops?location=Lanus | 4638  |   0(0.00%)  |  4186   |   27 |  42928  |    1400 |  12.50 |
|------|----------|-----------|---------|--------|--------|--------|--------|
|Total |   25384  |   0(0.00%)|         |        |        |        |  72.00 |

- Percentage of the requests completed within given times

| Name  |  # reqs  |  50%  |  66% |   75%  |  80%  |  90%  |  95%  |  98%  |  99% |   100% |
|-------|----------|-------|------|--------|-------|-------|-------|-------|------|-----------|
| POST /api/v1/found-prices | 9253 |  1700 |  3200 |  4700 |  6100 | 16000 | 18000 | 21000 | 26000 | 42154 |
| POST /api/v1/shops  | 2378 |  1500 |  2900 |  4200 |  5800 | 16000 | 18000 | 23000 |  26000 | 42124 |
| GET /api/v1/shops?location=Berazategui | 4624  | 1400 |  2800 |  4200 |  5600 | 16000 |  18000 | 22000 | 25000 | 41508 |
| GET /api/v1/shops?location=Bernal | 4491 |  1500 |  3100 |  4800  | 6600 | 16000 | 18000 | 21000 | 26000 | 41874 |
| GET /api/v1/shops?location=Lanus | 4638 |  1400 |  2900 |  4400 |  5800 | 16000 | 18000 |  22000 | 26000 | 42928 |


------
2) Sin cache: 

- El tiempo de respuesta se mantiene estable y por debajo de los 100 ms. Al siguiente minuto de terminar el período de rampa los tiempos comienzan a subir.
- Luego de 13 minutos de comenzado el test esta suba se hace considerable visualizando en ese punto 120 ms por request sobre 10969.2 transacciones. 

- No hubo alerta por Apdex Score.

- Ya pasado el tiempo de rampa(10 minutos), durante el hold-for del test se vé un pico de 568 ms por request sobre 17 transacciones. 
Si bien esto hace parecer que cada transación tarda un tiempo considerable, durante el hold-for también hubo un punto donde se realizaron 2028.3 transacciones con un tiempo de 296 ms por request

- El pico de request es de 11590.0 con 27.3 ms por request.
- Total 129317 transacciones.
- Resumen de requests:

| Name | # reqs  |    # fails  |   Avg  |   Min  |   Max  |  Median |  req/s |
|------|---------|-------------|--------|--------|--------|---------|--------|
| POST /api/v1/found-prices | 28855  |   0(0.00%)  |   139  |     4 |   1465  |      90 |  63.50 |
| POST /api/v1/shops | 7185  |   0(0.00%) |     79  |     4  |  1059  |      25  | 15.10 |
| GET /api/v1/shops?location=Berazategui | 14376  |   0(0.00%)  |    73   |    2  |  1256  |      20 |  30.90 |
| GET /api/v1/shops?location=Bernal | 14539  |   0(0.00%)  |    74   |    2  |  1296  |      22 |  30.30 |
| GET /api/v1/shops?location=Lanus | 14491  |   0(0.00%)  |    73   |    2  |  1268  |      21 |  35.50 |
|-----|--------|--------------|--------|---------|------|----------|--------|
|Total| 79446  |   0(0.00%)   |        |         |      |          | 175.30 |

- Percentage of the requests completed within given times

| Name  |  # reqs  |  50%  |  66%  |  75%  |  80%  |  90%  |  95%  |  98%  |  99% |  100% |
|-------|----------|-------|-------|-------|-------|-------|-------|-------|------|------|
| POST /api/v1/found-prices | 28855  |   90  |  140  |  180  |  220  |  330 |   440 |   580 |   680 |  1465 |
| POST /api/v1/shops | 7185  |   25  |   58  |   92  |  120  |  230 |   350  |  520 |   600 |  1059 |
| GET /api/v1/shops?location=Berazategui | 14376  |   20  |   50  |   85  |  110 |   220 |    330  |  480 |   580 |  1256 |
| GET /api/v1/shops?location=Bernal |  14539  |   22  |   53  |   86  |  110  |  220 |   340  |  480  |  580 |  1296 |
| GET /api/v1/shops?location=Lanus | 14491  |   21  |   52  |   86  |  110 |   210  |  330  |  480  |  580 |  1268 |



2) Con cache: 

- Se visualiza un primer pico al comenzar el test de 172 ms por request sobre 180.2 transacciones. Se cree que este pico es por el llenado de la caché de requests.

- Inmediatamente luego del pico anterior el tiempo de respuesta se mantiene estable y por debajo de los 50 ms. 
- Al siguiente minuto de terminar el período de rampa los tiempos comienzan a subir.
Luego de 13 minutos de comenzado el test esta suba se hace considerable visualizando en ese punto 65 ms por request sobre 10937.3 transacciones. 

- No hubo alerta por Apdex Score.

- Ya pasado el tiempo de rampa(10 minutos), durante el hold-for del test se vé un pico de 368 ms por request con 200.9 transacciones. 
Si bien esto hace parecer que cada transacción tarda un tiempo considerable, durante el hold-for también hubo un punto donde se realizaron 10937.3 transacciones con un tiempo de 65.3 ms por request.

- El pico de request es de 11530.4 con 26.1 ms por request.
- Total 136199 transacciones.

----

### **Conclusión**

Lo más significativo entre estos 2 casos es la diferencia en la cantidad de transacciones:
*1) 55605,5* (promedio con y sin caché)
*2) 132758* (promedio con y sin caché)
Lo que quiere decir que con 2 nodos de CPU se procesan más del doble de transacciones.
Además del hecho de que en el escenario 2 no hay alerta por [Apdex Score](https://docs.newrelic.com/docs/apm/new-relic-apm/apdex/apdex-measuring-user-satisfaction#score)

Otro factor es la diferencia durante los picos de request:
*1) 5525,1 transacciones con 801,5 ms por request* (promedio con y sin caché)
*2) 11560,2 transacciones con 26,7 ms por request* (promedio con y sin caché)
Nuevamente el caso 2, más del doble de request con un tiempo ínfimo en comparación al caso 1.

También se puede ver la diferencia durante los picos de tiempo por request:
*1) 6180 ms sobre 906,2 transacciones* (promedio con y sin caché)
*2) 468 ms sobre 108,95 transacciones* (promedio con y sin caché)
En este caso habría que ver la causa de por qué tan pocas request causaron un pico de tiempo en el caso 2.


