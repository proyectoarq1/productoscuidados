# Scrip para levantar la aplicación en docker con una base mongo
error_exit () {
	echo "$1" 1>&2
	exit 1
}
echo "--Ejecutando script--"
echo "Generando la imagen del proyecto"
./activator docker:stage || error_exit "Error staging image"
./activator docker:publishLocal || error_exit "Error publishing image"
echo "--Se ha generado la imagen--"
echo "** Para correrla ejecuta el archivo 'run-docker-play'"