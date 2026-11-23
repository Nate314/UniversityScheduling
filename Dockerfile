# Compiles the Java sources (saved as Windows-1252 by Eclipse on Windows), then runs the simulated annealing scheduler once and exits.
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY src ./src
RUN javac -encoding windows-1252 -d out $(find src -name '*.java')

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/out ./out
ENTRYPOINT ["java", "-cp", "out", "com.nathangawith.umkc.UniversityScheduling"]
