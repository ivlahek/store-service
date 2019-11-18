docker run -d -p 15432:5432 postgres:9.6.9-alpine
TIMEOUT 10
java -jar %cd%/store-service-1.0-SNAPSHOT.jar