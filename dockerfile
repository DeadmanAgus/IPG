FROM openjdk:11.0.3-jre-slim-stretch

WORKDIR /opt

COPY target/*.jar /opt/app.jar
EXPOSE 8090
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
