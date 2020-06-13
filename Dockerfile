FROM openjdk:11

ARG JAR_FILE=build/libs/SpringMovies-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} spring-movies.jar

ENTRYPOINT ["java", "-jar", "spring-movies.jar"]