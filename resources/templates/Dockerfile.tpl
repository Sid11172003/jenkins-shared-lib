FROM openjdk:17-jdk
WORKDIR /app
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
