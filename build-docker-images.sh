projectDirectory=$1
dockerImageTag=$2
#project_dir|image_tag
arr=("accountmanager|account_manager_service" "giphermanager|gipher_manager_service" "gipherrecommendersystem|gipher_recomender_service")

echo "===================================================================================="
echo " Building Docker Images "
echo "===================================================================================="
echo " Total images to be Build: ${#arr[@]}"
echo "************************************************************************************"
for i in "${arr[@]}"
do
	echo $i
	IFS='|' read -r -a array <<< "$i"
	#echo ${array[0]}
	#echo ${array[1]}
	projectDirectory=${array[0]}
	dockerImageTag=${array[1]}
	totalEntries="${#array[@]}"
	sh ./build-docker-image.sh $projectDirectory $dockerImageTag
	#echo $totalEntries
	
done
 