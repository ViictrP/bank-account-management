FROM gradle:6.6.0-jdk11 AS build

ARG PORT
ARG DB_HOST
ARG DB_PORT
ARG DB_USER
ARG DB_PASSWORD
ARG DEFAULT_SCHEMA

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

ENV PORT=$PORT
ENV DB_HOST=$DB_HOST
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD
ENV DEFAULT_SCHEMA=$DEFAULT_SCHEMA

FROM openjdk:11-jre-slim

RUN mkdir /app && \
    groupadd app && \
    useradd app -u 1001 -g app -d /app -s /bin/bash

RUN chown 1001:app /app -R

COPY --from=build /home/gradle/src/application/build/*.jar /app/

CMD java -jar /app/bank-account-management.jar

EXPOSE 8080