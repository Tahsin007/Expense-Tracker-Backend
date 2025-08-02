## Use the official OpenJDK image as the base
#FROM openjdk:17-jdk
#
## Set working directory in container
#WORKDIR /app
#
## Copy the JAR file from your local target folder into the container
#COPY target/Expense-Tracker-0.0.1-SNAPSHOT.jar app.jar
#
## Run the JAR file when the container starts
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Stage 1: Build the app
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
#ENTRYPOINT ["java", "-Dserver.port=$PORT", "-jar", "app.jar"]
CMD java -Dserver.port=$PORT -jar app.jar
