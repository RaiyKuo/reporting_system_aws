# This is a PowerShell script to build all three services into docker images.
# The docker engine has to be running before starting.
cd "D:\Coding\Java\reporting_system_aws"

# Copy AWS credentials into Dockerfile folder for building
copy-item "C:\Users\Xaiyk\.aws\credentials" -destination ".\ClientService\docker\"
copy-item "C:\Users\Xaiyk\.aws\credentials" -destination ".\ExcelService\docker\"
copy-item "C:\Users\Xaiyk\.aws\credentials" -destination ".\PDFService\docker\"

# Build docker images
mvn clean install "-Dmaven.test.skip=true"

exit


