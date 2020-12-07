#!/bin/bash
projectDirectory=$1
dockerImageTag=$2
echo "===================================================================================="
echo " Building Project & Docker Image "
echo "===================================================================================="
echo "Your Project Directory     : $projectDirectory"
echo "Your Docker image Tag Name : $dockerImageTag"
echo "===================================================================================="
ui="ui";
if [ "$dockerImageTag" = "$ui" ]
then
	echo "Gipher UI So No Maven"
	cd $projectDirectory
	./build_deploy.sh
	cd ..
else
	mvn clean install -Dmaven.test.skip=true -f ./$projectDirectory/pom.xml
	if [ -z "$dockerImageTag" ]
	then
		echo "Skipping deploying in Docker : $projectDirectory"
		
	else
		echo "Building Docker of $projectDirectory"
		
		
		#export DOCKER_HOST=tcp://192.168.1.11:2375

		# Stop Container incase running
		cd $projectDirectory
		./build_image.sh
		cd ..
		#docker  image build -t $dockerImageTag ./$projectDirectory/. 
	fi
	echo "===================================================================================="
fi

# Building Docker Image
echo "===================================================================================="
echo "Building Docker Image   from $projectDirectory"
echo "===================================================================================="
