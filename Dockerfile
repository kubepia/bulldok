# FROM maven:3.6.3-jdk-8-slim as build
# WORKDIR /app

# # COPY mvnw .
# # COPY .mvn .mvn
# COPY pom.xml .
# COPY src src

# RUN mvn install -DskipTests


From openjdk:8-jdk-alpine
VOLUME /tmp
WORKDIR /app
ADD target/*.jar app.jar
# COPY --from=build /app/target/*.jar /app/

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prd","-jar","app.jar"]