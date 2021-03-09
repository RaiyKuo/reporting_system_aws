docker network rm reporting_service_docker_bridge

# Create a docker network bridge.
docker network create reporting_service_docker_bridge

# Add all three service containers into the network bridge.
# NOTE: The hostname must be the same with the project name, otherwise will cause HTTP 400 bad request denial.
docker create --name client_service_container `
--network reporting_service_docker_bridge `
--hostname ClientService `
--publish 8080:8080 `
client_service:latest

docker create --name excel_service_container `
--network reporting_service_docker_bridge `
--hostname ExcelService `
--publish 8888:8888 `
excel_service:latest

docker create --name pdf_service_container `
--network reporting_service_docker_bridge `
--hostname PDFService `
--publish 9999:9999 `
pdf_service:latest