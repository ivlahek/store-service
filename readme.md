#Store service
## Running a store service
You will need maven and docker installed locally on your computer in order to run the service with the following script

`run-script.bat` or `run-script.sh`
- will run postgres SQL database in docker on 15432 port 
- will build application using maven
- will run application on a port 8080

If you don't have maven and docker installed locally on your computer you can download application from:
`https://github.com/ivlahek/store-service/packages/158317`

Application by default:
- Application is listening on a port 8080
- Application needs postgres database listening on a port 15432
- Application can be run `java -jar store-service-1.0-20200320.193446-1.jar`

## API - documentation
- api documentation is available on http://localhost:8080/swagger-ui.html
## Maven 
- maven build can be run the build (including unit and integration tests) with the command `mvn clean install -Pjenkins`
