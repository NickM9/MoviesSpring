FROM tomcat:9-jdk11

COPY ./build/libs/SpringMovies-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/spring-movies.war

