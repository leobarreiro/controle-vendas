#!/bin/sh
# docker build -t @project.artifactId@:@project.version@ .
# docker run --name @project.artifactId@ -m 256m -d -p 8082:8082 -p 9092:9092 @project.artifactId@:@project.version@
docker run --name leitor-arquivo-mongo -d -p 27017:27017 -m 128m -e MONGODB_ROOT_PASSWORD=root123 -e MONGODB_USERNAME=leitor-arquivo -e MONGODB_PASSWORD=RoosTarpals831. -e MONGODB_DATABASE=leitor-arquivo -v /usr/local/docker/volumes/mongo:/bitnami bitnami/mongodb:4.2.2




docker run --name leitor-arquivo-postgres -d -p 5432:5432 -m 256m postgres:11.2-alpine

