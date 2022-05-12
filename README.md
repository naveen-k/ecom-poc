# api-catalog-sales
This repository contains the Sales Catalog service that provides the ability to persist product assignments to store on the test Digital Platform. It will also allow Merchandiser to make necessary changes to prepare the sales catalogs for deployment to customer-facing systems.

# Repo Owner
 FeatureTeam1

# Repo Type
Maven repository based on Spring Boot.

# Codefresh Variables
N/A

##Steps to install in Containerised Environment:
 Compile and build docker image
 ```
 mvn clean package
 ```
 Docker image creation:

 * Local Containerized Environment Creation:
 ```
  docker build -t api-catalog-sales:v1 -f Dockerfile-local .
 ```
 * Containerized Environment Creation:
 ```
 docker build . -t api-catalog-sales:v1 -f Dockerfile
 ```

 Install on K8S:
 ```
 helm install api-catalog-sales ./api-catalog-sales --values ./api-catalog-sales/values-local.yaml --namespace commerce
 ```

 Logs:
 ```
 kubectl logs api-catalog-sales-<97bccf6-hjsxj> -n commerce -c api-catalog-sales -f
 ```
 To check the istio gateway:
 ```
 kubectl -n istio-system get -l app=istio-ingressgateway --no-headers pods -o custom-columns=:metadata.name
 ```
 To check health
 ```
 ~ http://localhost:31219/api/commerce/salescatalog/actuator/health
 ```
 To check info
 ```
 ~ curl http://localhost:31219/api/commerce/salescatalog/actuator/info
 ```
To delete the pod
```
helm --namespace commerce del api-catalog-sales
```
# Code coverage commands
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true -P coverage ( This assumes that a maven profile by name 'coverage' exists)
mvn sonar:sonar -X

 # Sales Catalog Implementation

 The service currently performs the following tasks:
 1. Provides the ability to persist product assignments to store on the test Digital Platform.
 2. Allows merchandiser to make necessary changes to prepare the sales catalogs for deployment to customer-facing systems.


 ## API URIs

 * Local environment: **/api/v1/salescatalog/products**
 * Containerized environment: **api/commerce/salescatalog/v1/product**

 ## Getting Started

 The microservice consists of the following modules:
 * api - This module contains the exposed end points for the sales catalog microservice.
 * app - Spring Boot uses this module to package everything as a deployable container.
 * client - This module provides the client for consumers of the sales catalog microservice.
 * postman - This module contains the postman collection which can be used to invoke the application flows.
 * domain - This module contains the services to get the location specific sales offerings for various storefronts
|