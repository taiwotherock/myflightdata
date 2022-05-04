# myflightdata #


## Introduction ##
This is a spring boot microservice built with gradle and run on JDK 11.  
The application receives CSV file upload via HTTP REST Controller method read the file, process it on the file 
It is advisable to open and run the project using Intellij IDE

## To test it ##
Run this command using gradlew

gradlew clean test --info

You can also test this service using Postman collection by calling endpoint below using GET method

http://localhost:8084/flightdata/flights?flightDate=2022-04-30&flightFileName=flights.csv

## Swagger Open API Documentation ##

http://localhost:8084/swagger-ui/index.html#/flightdata/getFlightInformationDisplay