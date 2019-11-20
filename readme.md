#Store service
## Running a store service
`run-script.bat` 
- will run postgres SQL database in docker on 15432 port 
- will run application on a port 8080
## API - documentation
- api documentation is available on http://localhost:8080/swagger-ui.html
## Maven 
- maven build can be run the build (including unit and integration tests) with the command `mvn clean install -Pjenkins`
## API authentication
Api authentication is not implemented but I would use JWTs.
In the JWT token we can encode all relevant parts of an access token into the access token itself instead of having to store them in a database

By doing this we are removing the pressure from the database, because the data about the user is located in the JWT, not in the database.

By doing this our services no longer need to implement authentication on they own. This can be time consuming in even small systems. 
Imagine that every service should implement authentication on they own. And imagine that every service is implemented by different team. 
Even if they are instructed how to build it, every team will have some custom improvements. 

By using JWTs we use a well know standard that is used by the many companies in the world and which is supported by many frameworks.

## Redundancy
If we want our services to be redundant, we must make sure our applications can work simultaneously on multiple nodes.
Our services should be stateless as much as possible, and should never keep their state saved locally.
Our client's requests should be processed by any of the service instances.
