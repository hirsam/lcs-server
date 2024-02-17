FROM openjdk:17-oracle

WORKDIR /app

COPY target/lcs-server*.jar /app/lcs-server.jar

EXPOSE 8081

CMD ["java", "-jar", "lcs-server.jar"]