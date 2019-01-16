#!/bin/sh

mvn clean package
docker build -t img-upload-app:1.0 .
docker run -it --rm -p 8080:8080 img-upload-app:1.0



