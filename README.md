# Twitter playground project

This repository implements a tiny service, with a rest controller, that performs some basic operations 
towards Twitter public API. Twitter4J is used for all twitter accesses. (See http://twitter4j.org/en/index.html)

## Credentials
Add your credentials in a file called "twitter4j.properties", place it in the root directory with
the following 4 variables (Get your keys here: https://developer.twitter.com/en/portal/dashboard): 

* debug=true
* oauth.consumerKey=******************** 
* oauth.consumerSecret=********************
* oauth.accessToken=********************
* oauth.accessTokenSecret=********************

## Build from command line
 * \>\> gradle clean build

## Launch from command line
Make sure Java 11 is available in path. 
 * \>\> gradle bootRun
 
## Endpoint Swagger documenation
 http://127.0.0.1:8080/swagger-ui/
 
## Building and running the service as a Docker container
 * \>\> docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
 * \>\> docker run --rm --network host -p 8080:8080 springio/gs-spring-boot-docker:latest
