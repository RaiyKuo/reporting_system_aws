# This script is use to push all three docker images to AWS ECR repository.

# get-login-password
(Get-ECRLoginCommand).Password | docker login --username AWS `
--password-stdin 474850310752.dkr.ecr.us-east-1.amazonaws.com

# Push images to corresponding ECR repository.
docker tag client_service 474850310752.dkr.ecr.us-east-1.amazonaws.com/client_service:latest
docker push 474850310752.dkr.ecr.us-east-1.amazonaws.com/client_service:latest

docker tag excel_service 474850310752.dkr.ecr.us-east-1.amazonaws.com/excel_service:latest
docker push 474850310752.dkr.ecr.us-east-1.amazonaws.com/excel_service:latest

docker tag pdf_service 474850310752.dkr.ecr.us-east-1.amazonaws.com/pdf_service:latest
docker push 474850310752.dkr.ecr.us-east-1.amazonaws.com/pdf_service:latest