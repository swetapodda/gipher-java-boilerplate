#!/bin/bash
projectDirectory=$1
dockerImageTag=$2
#project_dir|image_tag
arr=("netflix-eureka-naming-server|a" "netflix-zuul-api-gateway-server|b" "accountmanager|c" "giphermanager|d" "gipherrecommendersystem|e" "GipherUI|ui")
echo ""
echo ""
echo "===================================================================================="
echo " Building Porject & Docker Images "
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
	sh ./build-docker-image_by_file.sh $projectDirectory $dockerImageTag
	#echo $totalEntries
	
done
 