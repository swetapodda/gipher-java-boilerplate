FROM java:8-jre
WORKDIR usr/src/

# My SQL Property need to set in Docker Compose file

ADD ./target/giphermanager-1.0.jar /usr/src/giphermanager-1.0.jar
ENTRYPOINT ["java","-jar","giphermanager-1.0.jar"]

# ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongocontainer/notedb","-jar","netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar"]