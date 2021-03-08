FROM openjdk:15
ADD /excel_service-1.0-SNAPSHOT.jar //
# copy AWS credentials into docker image. The default location ~/.aws/ is equal to /root/.aws
COPY /credentials /root/.aws/
ENTRYPOINT ["java", "-jar", "/excel_service-1.0-SNAPSHOT.jar"]
HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8888/health || exit 1
