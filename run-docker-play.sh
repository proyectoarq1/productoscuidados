answer=""
memoryLimit="0"
cpuCores="0"
cpuProportion="1024"
swapMemory="-1"
mongoNodes="1"
mongoNodesSetted="0"
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
	#
	echo "Enter Swap Memory: "
	read swapMemory
	if [ "$swapMemory" = "" ];then
		swapMemory="-1"
	fi
	echo "Sm -> $swapMemory"
	#
	echo "Enter Mongo Nodes 1-3: "
	read mongoNodes
	if [ "$mongoNodes" = "" ];then
		mongoNodes="1"
	fi
	echo "Mongo Nodes -> $mongoNodes"
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
echo "--Executing script--"


echo "corriendo el proyecto play...."

if [ "$mongoNodes" = "1" ];then
	echo "Pulling and running mongo image"
	docker pull mongo

	echo "Removing existing mongo images"
	docker rm -f $mongoname

	docker run -d --name $mongoname mongo
	
	mongoNodesSetted="1"
	#Una instancia de mongo
	echo "Running container with one node of mongo"
	docker run -h "productosCuidadosDocker" -t -i -p 9000:9000 --rm -m $memoryLimit --memory-swap $swapMemory --cpu-shares=$cpuProportion --cpuset-cpus=$cpuCores --link $mongoname:$mongoname play-scala-2-4:1.0-SNAPSHOT bin/

fi

if [ "$mongoNodes" = "2" ];then
	echo "Pulling and running two mongo images"
	docker pull mongo

	echo "Removing existing mongo images"
	docker rm -f mongo1
	docker rm -f mongo2
	
	docker run -d --expose 27017 --name mongo1 mongo --replSet rsmongo
	docker run -d --expose 27017 --name mongo2 mongo --replSet rsmongo

	MONGODB1=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' mongo1)
	MONGODB2=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' mongo2)

	echo $MONGODB1
	echo $MONGODB2

	mongoNodesSetted="1"
	echo 'Conecting mongo1 with mongo2 in replica set'
	#docker exec mongo1 mongo --eval 'rs.initiate(); cfg = rs.conf(); cfg.members[0].host = "'$MONGODB1':27017"; rs.reconfig(cfg); rs.add("'$MONGODB2':27017"); rs.status();'
	docker exec mongo1 mongo --eval "rs.initiate();"
	sleep 5
	docker exec mongo1 mongo --eval "cfg = rs.conf(); cfg.members[0].host = '$MONGODB1:27017'; rs.reconfig(cfg);"
	sleep 5
	docker exec mongo1 mongo --eval "rs.add('$MONGODB2:27017');"
	sleep 5
	docker exec mongo1 mongo --eval "rs.status();"

	#Dos instancias de mongo
	echo "Running container with two nodes of mongo"
	docker run -h "productosCuidadosDocker" -t -i -p 9000:9000 --rm -m $memoryLimit --memory-swap $swapMemory --cpu-shares=$cpuProportion --cpuset-cpus=$cpuCores --link mongo1:mongo1 --link mongo2:mongo2 play-scala-2-4:1.0-SNAPSHOT bin/


fi

if [ "$mongoNodes" != "2" ] && [ "$mongoNodes" != "1" ] && [ "$mongoNodesSetted" = "0" ];then
	echo "Pulling and running three mongo images"
	docker pull mongo
	
	echo "Removing existing mongo images"
	docker rm -f mongo1
	docker rm -f mongo2
	docker rm -f mongo3
	
	docker run -d --expose 27017 --name mongo1 mongo --replSet rsmongo
	docker run -d --expose 27017 --name mongo2 mongo --replSet rsmongo
	docker run -d --expose 27017 --name mongo3 mongo --replSet rsmongo

	MONGODB1=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' mongo1)
	MONGODB2=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' mongo2)
	MONGODB3=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' mongo3)

	echo $MONGODB1
	echo $MONGODB2
	echo $MONGODB3

	echo 'Conecting mongo1 with mongo2 and mongo3 in replica set'
	#docker exec mongo1 mongo --eval 'rs.initiate(); cfg = rs.conf(); cfg.members[0].host = "$MONGODB1:27017"; rs.reconfig(cfg); rs.add("$MONGODB2:27017"); rs.add("$MONGODB3:27017"); rs.status();'
	docker exec mongo1 mongo --eval "rs.initiate();"
	sleep 5
	docker exec mongo1 mongo --eval "cfg = rs.conf(); cfg.members[0].host = '$MONGODB1:27017'; rs.reconfig(cfg);"
	sleep 5
	docker exec mongo1 mongo --eval "rs.add('$MONGODB2:27017'); rs.add('$MONGODB3:27017');"
	sleep 5
	docker exec mongo1 mongo --eval "rs.status();"

	#Dos instancias de mongo
	echo "Running container with three nodes of mongo"
	docker run -h "productosCuidadosDocker" -t -i -p 9000:9000 --rm -m $memoryLimit --memory-swap $swapMemory --cpu-shares=$cpuProportion --cpuset-cpus=$cpuCores --link mongo1:mongo1 --link mongo2:mongo2 --link mongo3:mongo3 play-scala-2-4:1.0-SNAPSHOT bin/

fi
