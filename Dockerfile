FROM eclipse-temurin:18-alpine
ARG APP_VERSION

RUN mkdir /app
WORKDIR /app
ADD docker-entrypoint.sh /app

ADD target/grpc-template-1.0-SNAPSHOT.jar /app/app.jar

ENV APP_VERSION ${APP_VERSION}
ENV DD_VERSION ${APP_VERSION}
EXPOSE 8080

ENTRYPOINT ["/app/docker-entrypoint.sh"]

