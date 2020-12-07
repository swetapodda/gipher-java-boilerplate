projectDirectory=$1
dockerImageTag=$2
echo "===================================================================================="
echo " Building Docker Image "
echo "===================================================================================="
echo "Your Project Directory     : $projectDirectory"
echo "Your Docker image Tag Name : $dockerImageTag"
echo "===================================================================================="

mvn clean install -Dmaven.test.skip=true -f ./$projectDirectory/pom.xml
# Building Docker Image
echo "===================================================================================="
echo "Building Image: $dockerImageTag from $projectDirectory"
echo "===================================================================================="
if [ -z "$dockerImageTag" ]
then
	echo "Skipping deploying in Docker : $projectDirectory"
	
else
	echo "Deploying in Docker: Image Tag:$dockerImageTag Project: $projectDirectory"
	
	
	#export DOCKER_HOST=tcp://192.168.1.11:2375

	# Stop Container incase running
	echo "Stopping Container : $dockerImageTag"

	docker  stop $(docker ps -q -a --filter ancestor="$dockerImageTag" )
	echo "===================================================================================="

	# Remove Container if exist
	echo "Removing Container : $dockerImageTag"
	docker  rm $(docker ps -qa --filter ancestor="$dockerImageTag")

	echo "===================================================================================="
	# Remove Image if Exist
	echo "Removing Image: $dockerImageTag"

	docker rmi $(docker images |grep "$dockerImageTag")
	echo "===================================================================================="
	echo "Buidling Spring Boot Application :  $projectDirectory" 
	echo "===================================================================================="

	##docker  image build -t $dockerImageTag ./$projectDirectory/. 
	cd  $projectDirectory
	./build_image.sh
	cd ..
fi
echo "===================================================================================="