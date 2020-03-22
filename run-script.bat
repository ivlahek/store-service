call docker run -d -p 15432:5432 postgres:9.6.9-alpine
call mvn clean install -DskipTests
java -jar %cd%/store-service/target/store-service-1.0.1.jar