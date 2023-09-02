# Party Planner Backend - 
The Party Planner App is a versatile and efficient tool designed to simplify the process of organizing and managing parties, events, and gatherings of all sizes.

### Basic infrastructure

Before we try to start the application, we should set up its basic infrastructure.
For this purpose, it is provided a [docker-compose.yml](/docker-compose.yml) file, which describes this basic infrastructure.
Currently, the infrastructure needed is a [PostgresSQL](https://www.postgresql.org/) database. Having [docker](https://www.docker.com/) and
[docker compose](https://docs.docker.com/compose/) installed in your environment, you should be able to provision the resources using the following command:

```sh
docker-compose up -d
```

on the other side, in order to stop these resources in your environment you should type

```sh
docker-compose stop
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

