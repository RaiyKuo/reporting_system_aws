# Antra SEP java evaluation project

## About This Version (v/MySQL+S3)

* There are 4 branches (versions) in the Github repository.
* This version replaces the embedded databases of all three services to persistable MySQL.

## Changes/Improvement I Made

### Features

* Changed the databases of all three services to independent local MySQL database.
* Fulfilled the functionality for deleting reports and files.

### Tests

* Added simple integration tests for the "creating" endpoints of both ClientService and ExcelService.

### Bug Fixes

* Moved Excel file location from local to S3 bucket
* Fixed a bug that Excel local file stream was not properly closed so that cannot be normally deleted.

Please see [CHANGELOG.md](./CHANGELOG.md) for further details if you like to.

## How To Run It

1. Put AWS credentials in "C:\Users\{USER}\.aws\credentials" or "~/.aws/credentials".
2. Setup a local MySQL database.
3. Run all three services.
5. Go to [http://localhost:8080](http://localhost:8080).

## About Me

**Hung-Jui Kuo**. Email: [xaiykou@gmail.com](xaiykou@gmail.com) 
