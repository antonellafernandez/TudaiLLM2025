#!/bin/bash

CONTAINER_ID=$(docker container ls -a | grep elasticsearch | cut -d" " -f1)
docker container stop $CONTAINER_ID && docker container rm $CONTAINER_ID
docker image rm elasticsearch:8.14.3
