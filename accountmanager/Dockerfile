FROM java:8-jre
WORKDIR usr/src/

# My SQL Property need to set in Docker Compose file

ADD ./target/accountmanager-1.0.jar /usr/src/accountmanager-1.0.jar
ENTRYPOINT ["java","-jar","accountmanager-1.0.jar"]

# ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongocontainer/notedb","-jar","netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar"]