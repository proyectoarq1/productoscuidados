answer=""
memoryLimit="0"
cpuCores="0"
cpuProportion="1024"
#
if [ "$1" != "" ]; then
	mongoname=$1
else
    mongoname="mongoplay"
fi
echo "mongo image name: $mongoname"
#
while [ "$answer" != "Y" ] && [ "$answer" != "N" ];
do 
	echo "Do you want customize docker container resources?(Y/N)"
	read answer
done
#
if [ "$answer" = "Y" ];then
	echo "Container resources:"
	echo "Enter memory limit: (ej:2G)"
	read memoryLimit
	if [ "$memoryLimit" = "" ];then
		memoryLimit="0"
	fi
	echo "M -> $memoryLimit"
	#
	echo "Enter CPU cores: "
	read cpuCores
	if [ "$cpuCores" = "" ];then
		cpuCores="1"
	fi
	cpuCores="0-$cpuCores"
	echo "Cpu -> $cpuCores"
	#
	echo "Enter CPU proportion cycles: "
	read cpuProportion
	if [ "$cpuProportion" = "" ];then
		cpuProportion="1024"
	fi
	echo "C -> $cpuProportion"
else
	echo "* Default values"
fi

#if [[ $2 != "" ]]; then
#	memoryLimit=$2
#else
#    memoryLimit="inf"
#fi
#if [[ $3 != "" ]]; then
#	cpuCores=$3
#else
#    cpuCores="1"
#fi
#if [[ $4 != "" ]]; then
#	cpuProportion=$4
#else
#    cpuProportion="1024"
#fi
#echo $mongoname
echo "--Ejecutando script--"
echo "Trayendo y corriendo imagen de mongo"
docker pull mongo
docker run -d --name $mongoname mongo
docker start $mongoname

echo "corriendo el proyecto play...."
docker run -h "productosCuidadosDocker" -t -i -p 9000:9000 --rm -m $memoryLimit --cpu-shares=$cpuProportion --cpuset-cpus=$cpuCores --link $mongoname:$mongoname play-scala-2-4:1.0-SNAPSHOT bin/