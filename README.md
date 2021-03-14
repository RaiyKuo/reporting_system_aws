# Antra SEP java evaluation project

## About This Version (v/Eureka)

* There are 4 branches (versions) in the Github repository.
* This version can run in local docker containers or on AWS ECS.

## Changes/Improvement I Made

### Features

* Added a script to upload the docker images of services to AWS ECR and running in ECS.
* Added dockerfile/docker-compose files and building scripts to make the system runnable in Docker.
* Added Multi-threading for simultaneously calling PDF and EXCEL services when generating/deleting SYNC.
* Fulfilled the functionality for deleting reports and files.
* Changed the databases of all three services to remote AWS DynamoDB.

### Tests

* Added simple integration tests for the "creating" endpoints of both ClientService and ExcelService.

### Bug Fixes

* Moved Excel file location from local to S3 bucket
* Fixed a bug that Excel local file stream was not properly closed so that cannot be normally deleted.

Please see [CHANGELOG.md](./CHANGELOG.md) for further details if you like to.

## How To Run It

1. Put AWS credentials in "C:\Users\{USER}\.aws\credentials" or "~/.aws/credentials".
2. Run the PowerShell script "build.ps1" to build all three services into docker images.
3. After the building successfully finished, run the PowerShell script "docker_network.ps1" to execute all three
   services in the pre-defined docker network "reporting_service_docker_bridge" allowing them communicate to each other.
4. Alternatively, you can also run the "docker-compose.yml" file to run the services.
5. Go to [http://localhost:8080](http://localhost:8080).

## About Me

**Hung-Jui Kuo**. Email: [xaiykou@gmail.com](xaiykou@gmail.com) 
