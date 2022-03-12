# api-rest-tasker

This project is a REST based backend to manage tasks built with [dropwizard](https://www.dropwizard.io/en/latest); it will persist the tasks in a postgresql database. 

## developing

We have chosen maven (https://maven.apache.org/) to manage dependencies and genereting the artifact for the project; following dropwizard recommendations we will distribute a single jar with all dependencies inside.

The bootstrap was made with a maven archetype for dropwizard in the following way:
```
mvn archetype:generate   -DarchetypeGroupId=io.dropwizard.archetypes   -DarchetypeArtifactId=java-simple -DarchetypeVersion=2.1.0-beta.7
```

We use docker-compose to launch the application locally and multistage dockerfiles; It's possible to debug from the container itself, or you just simply launch everything except the application and run the backend with your favourite ide or from command line. You can check the production container as well.

We have followed the recommended setup of project in dropwizard (https://www.dropwizard.io/en/latest/manual/core.html#organizing-your-project).

The database used is postgresql as it's widely used, have good performance and continous new features. We use dropwizard JDBI3 (https://www.dropwizard.io/en/latest/manual/jdbi3.html) to connect and manage data from database.

## building & running rest api

This project can be built alone with the parent docker-compose ```docker-compose build api-rest-tasker```` and also using maven ```mvn clean install```.

The dropwizard application can be also runned locally using `java -jar target/api-rest-tasker-1.0-SNAPSHOT.jar server config.yml`

You will need to startup the database as well `docker-compose up db` in order to start. (Check environment variables in docker-compose).

## endpoints

All endpoints are currently public (no requires authentication) as they won't be exposed publicly on the internet.

- [List tasks](./docs/list-tasks.md) :  ```GET /tasks```
- [Add task](./docs/add-task.md) :  ```POST /tasks```
- [List task](./docs/list-task.md) :  ```GET /task/{id}```
- [Update task](./docs/update-task.md) :  ```PUT /task/{id}```

## users

Currently only one user is supported, but we will include future for multiple users in future. It will be changes in the endpoints as well.

## database evolutions (future)

We plan to use liquibase to run database evolutions but not including directly in our dropwizard application. We think it's better to decouple the two components separately.
For liquibase we plan in use a Dockerfile to apply them.

## Healthchecks and Metrics (future)

Include a prometheus collector and a prometheus transformer.https://github.com/RobustPerception/java_examples/tree/master/java_dropwizard
https://stackoverflow.com/questions/52931289/expose-prometheus-metrics-in-dropwizard

To see your applications health enter url `http://localhost:8081/healthcheck`
