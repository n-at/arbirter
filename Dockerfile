FROM java:openjdk-8-jre

MAINTAINER Alexey Nurgaliev <atnurgaliev@gmail.com>

WORKDIR /app
ADD target/arbirter.jar /app/arbirter.jar

RUN mkdir -m 0777 public logs

VOLUME ["/app/public", "/app/logs"]
EXPOSE 8080

CMD java -jar arbirter.jar
