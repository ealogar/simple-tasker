
FROM maven:3.5.4-jdk-8-alpine as dev

WORKDIR /opt/app
COPY pom.xml ./pom.xml

RUN mvn verify --fail-never

COPY src ./src

RUN mvn package

RUN ls -lart

CMD java -jar /opt/app/target/api-rest-tasker-1.0-SNAPSHOT.jar server config.yml

FROM openjdk:11 as prod
COPY --from=dev /opt/app/api-rest-tasker/target/api-rest-tasker-1.0-SNAPSHOT.jar ./
COPY --from=dev /opt/app/api-rest-tasker/config.yml ./

CMD java -jar api-rest-tasker-1.0-SNAPSHOT.jar server config.yml
EXPOSE 8080