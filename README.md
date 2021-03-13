# Antra SEP java evaluation project

## About This Version (v/Eureka)

* There are 4 branches (versions) in the Github repository.
* This one is the **Final one** (and the default branch), which contains all the features and improvements I did.
* This version can run with Eureka as micro-services, but can also run in local docker containers or on AWS ECS.

## Changes/Improvement I Made

### Features

* Added central config server and Ribbon/Eureka to make the system an extendable micro-services architecture.
* Added a script to upload the docker images of services to AWS ECR and running in ECS.
* Added dockerfile/docker-compose files and building scripts to make the system runnable in Docker.
* Added Multi-threading for simultaneously calling PDF and EXCEL services when generating/deleting SYNC.
* Fulfilled the functionality for deleting reports and files.
* Added websocket for automatically refreshing the webpage after ASYNC generating was completed.
* Changed the databases of all three services to remote AWS DynamoDB.

### Tests

* Added simple integration tests for the "creating" endpoints of all three services.

### Bug Fixes

* Moved Excel file location from local to S3 bucket
* Fixed a bug that Excel local file stream was not properly closed so that cannot be normally deleted.
* Changed to only send emails when successfully generating reports.

Please see [CHANGELOG.md](./CHANGELOG.md) for further details if you like to.

## How To Run It

1. Put AWS credentials in "C:\Users\{USER}\.aws\credentials" or "~/.aws/credentials".
2. Start the "ConfigService" first, then the "ServiceDiscovery".
3. Start "ClientService", "ExcelService" and "PDFService".
4. Go to [http://localhost:8080](http://localhost:8080).

## About Me

**Hung-Jui Kuo**. Email: [xaiykou@gmail.com](xaiykou@gmail.com) 
