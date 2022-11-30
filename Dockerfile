FROM openjdk:8

COPY soap-service.jar /user/apps.jar

EXPOSE 8080

CMD ["java", "-jar", "/user/apps.jar"]