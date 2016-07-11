##Especificación

Se han corrido 2 series de pruebas(como lo indican los nombres de las carpetas):
- En la primer serie se corren todos los tests mencionados primero con una versión sin caché y luego otra con caché. Osea que para cada caso mencionado hay 2 tests y 2 informes distintos.
- En la segunda serie se corren los test ya sin caché pero agregando datos de memoria y CPU del servidor donde se corre cada test. Además se implementó una forma de manejo de request, determinando la cantidad que se aceptan y la cantidad que se pueden encolar. En caso de superar esa cantidad se arroja un error 429.