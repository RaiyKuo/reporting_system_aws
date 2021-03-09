# PDF service require New Times Roman font -> Use Ubuntu with fonts and Openjdk15 installed.
FROM ubuntu_with_java15:latest
ADD /pdf_service-1.0-SNAPSHOT.jar //
# copy AWS credentials into docker image. The default location ~/.aws/ is equal to /root/.aws
COPY /credentials /root/.aws/
ENTRYPOINT ["java", "-jar", "/pdf_service-1.0-SNAPSHOT.jar"]
HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:9999/health || exit 1
