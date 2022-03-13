
FROM maven:3.8.4-openjdk-11 as dev

WORKDIR /opt/app

COPY config.yml ./config.yml

COPY pom.xml ./pom.xml

RUN mvn verify --fail-never

COPY src ./src

RUN mvn package

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/target/api-rest-tasker-1.0-SNAPSHOT.jar", "server", "config.yml"]

FROM openjdk:11 as prod

WORKDIR /opt/app

COPY --from=dev /opt/app/target/api-rest-tasker-1.0-SNAPSHOT.jar ./api-rest-tasker-1.0.jar
COPY --from=dev /opt/app/config.yml ./

CMD ["java", "-jar", "/opt/app/target/api-rest-tasker-1.0.jar", "server", "config.yml"]

EXPOSE 8080
