FROM openjdk:17-oracle
COPY ./build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM openjdk:17
#COPY ./build/libs/airDnS-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]