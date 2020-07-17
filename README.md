# Find Pharmacy API
This api is responsible for accepting a location payload that contains a latitude and longitude and returning a pharmacy close by.
This project makes use of the Haversine algorithm for calculation of the distance in miles.

This Project is based upon a Java Spring Boot Framework and leverages concepts like JPA and WebMVC.

[Architecture](Documentation/findPharmacyArchitecture.png)

[WorkFlows to Run in order](Documentation/findPharmacyWorkflow.png)
## Getting Started

There are multiple ways of running this project, you can run through a jar file from the target/Build folder
Or you can import the maven pom file into your IDE and use that to build and run. 

### Prerequisites

We are going to need the following to run the project.

1. Java 11
2. MVN > 3.0
3. MySql Server

#### Adding MySql Database Connection
Under src/resources/application.properties file please enter in credentials for your mysql database.
Next, launch in the IDE for your choice and build. Or, you can build using ```mvn clean install```

[MySql Connection Example](Documentation/MySqlConnection.png)

#### Postman Collection for running the API
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6b6484f5b29ff631a786)

## Demo
Run the code with correct application.properties containing valid database connection.
Once the application starts successfully, you will be able to see a database called [pharmacy_database](Documentation/InitializedDatabase.png) containing a pharmacies table.

Next, run the [Postman query](Documentation/PopulatingDatabase.png) to upload file in the database
We can now see the [populated database](Documentation/PopulatedDatabase.png)

Next, we will make a findPharmacy API call
We can see that it returns details about the [closest pharmacy](Documentation/FindPharmacyCall.png)

### Future Improvements:
1. Create Embeddable credentials file
2. Write Integrations tests
3. Resolve CORS issue when running locally with chrome
