#Set argument HOME to use cross multiple stages
ARG HOME=/home/demo-gcp-pubsub
#
# Build stage
#
#FROM maven:3.8.3-openjdk-17 AS build
FROM maven:3.9-eclipse-temurin-17-alpine AS build
ARG HOME
#Create app folder
RUN mkdir -p $HOME
WORKDIR $HOME
#Copy source to app folder/src
COPY src $HOME/src
COPY pom.xml $HOME

#Maven build
RUN mvn -f $HOME/pom.xml package -DskipTests

#
# Package stage
#
#FROM openjdk:17-ea-jdk-oracle
FROM openjdk:17-alpine
ENV PORT 80
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -Xms32m -Xmx128m"
EXPOSE 80

ARG HOME
COPY --from=build $HOME/target/demo-gcp-pubsub-0.0.1-SNAPSHOT.jar /app/demo.jar
ENTRYPOINT java $JAVA_OPTS -jar /app/demo.jar