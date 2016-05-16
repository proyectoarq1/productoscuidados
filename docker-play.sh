if [[ $1 != "" ]]; then
	mongoname=$1
else
    mongoname="mongoplay"
fi
echo $mongoname
# Scrip para levantar la aplicación en docker con una base mongo
echo "--Ejecutando script--"
echo "Trayendo y corriendo imagen de mongo"
docker pull mongo
docker run -d --name $mongoname mongo
docker start $mongoname
echo "Generando la imagen del proyecto"
./activator docker:stage
./activator docker:publishLocal
echo "corriendo el proyecto"
docker run -t -i -p 9000:9000 --rm --link $mongoname:$mongoname play-scala-2-4:1.0-SNAPSHOT bin/