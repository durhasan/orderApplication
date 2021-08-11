# ReadingIsGood online book store order application
## Overview
This is a spring boot web application uses mongodb as database. 
The application also suports containerization.
It is a simple simulation for online book retail firm. 
The main purpose is create Restful endpoint to maintain functionalities:
* Registering New Customer
* Placing a new order
* Tracking the stock of books
* List all orders of the customer
* Viewing the order details
* Query Monthly Statistics

## Tech Stack
* Java 11
* Spring Boot 2.5.3
* MongoDb 4.2.3
* Spring Starter Web
* Docker Desktop Engine v20.10.7
* Lombok
* Postman
* Maven Wrapper

##Containerize
* Docker is used to containerize the application
* To run the application on docker container you can follow the building steps below.


## Building&Running Application (Windows)
* $PROJECT_PATH is the application root folder. Fistly open a Command Window and go to project directory.

    >cd $PROJECT_PATH
* Using Maven Wrapper to build the application.
  >mvnw.cmd clean install

* To build the application docker image use Dockerfile in the root directory. 
  >docker build -t api-docker-image .
* To compose with mongo docker image and application docker image run the command below. 
  >docker-compose -f $PROJECT_PATH\docker-compose.yml up -d
* Now it is ready to and acessible under port 4000 at localhost. To test use list books get method: http://localhost:4000/api/book/list 
##Sample Requests
* You can use Postman to send requests and test the applicaiton, Postman requests are attached under src/main/resources/static folder.