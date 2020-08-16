FROM openjdk:11.0.3-jre-slim-stretch

WORKDIR /opt

COPY target/iotecha-protocol-gateway-0.0.1-SNAPSHOT.jar /opt/app.jar
EXPOSE 8090
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
