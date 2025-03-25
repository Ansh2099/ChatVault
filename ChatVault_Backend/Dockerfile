# Use a lightweight Java 21 JDK image for building
FROM eclipse-temurin:21-jdk-jammy AS build

# Set work directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Ensure Maven wrapper is executable
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot application
RUN ./mvnw package -DskipTests

# Rename built JAR explicitly before copying
RUN mv target/*.jar target/app.jar

# Use a smaller JRE image for production
FROM eclipse-temurin:21-jre-jammy AS runtime

# Set work directory
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/app.jar app.jar

# Expose port
EXPOSE 8080

# Set memory limits for low RAM environments
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=65"

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]