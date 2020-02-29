FROM openjdk:8-jdk

EXPOSE 8080

VOLUME /tmp
ADD maven/dito-producer-0.0.1-SNAPSHOT.jar myapp.jar
RUN sh -c 'touch /myapp.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]