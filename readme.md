#Store service
## Building and running the store service (maven and docker necessary)
You will need maven and docker installed locally on your computer to build and run the service.
Run the following script to build and run the service:

`run-script.bat` or `run-script.sh`
- will run Postgres SQL database in docker on 15432 port 
- will build the application using maven
- will run the application on a port 8080

## Running the service manually (Postgres Database necessary)
If you don't have maven and docker installed locally on your computer you can download the application from:
`https://github.com/ivlahek/store-service/packages/158317`
You will need a postgres database listening on a port 15432. One can be ran in docker `docker run -d -p 15432:5432 postgres:9.6.9-alpine`

The application by default:
- is listening on a port 8080
- needs postgres database listening on a port 15432
- can be run `java -jar store-service-1.0-20200320.193446-1.jar`

## API - documentation
- API documentation is available on http://localhost:8080/swagger-ui.html

## Maven build 
- maven build can be run the build (including unit and integration tests) with the command `mvn clean install -Pjenkins`
- maven build will in test phase run unit tests
- maven build will in pre-integration phase run postgres database on a random port
- maven build will in integration phase run integration tests against database

## Local testing
- open project in your favorite IDE (tested only in IntelliJ Idea)
- run postgres on 5432 port `docker run -d -p 5432:5432 postgres:9.6.9-alpine`
- run test locally
