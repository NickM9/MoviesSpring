FROM openjdk:11

ADD build/libs/SpringMovies-1.0-SNAPSHOT.jar movies.jar

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000", "-jar", "movies.jar"]