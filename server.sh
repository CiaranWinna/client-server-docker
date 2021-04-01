#!/bin/bash
docker-compose build
printf "\nDocker has successfully build user network, containers and associated volumes!\n\n"
printf "\nServer is running!\n"
docker-compose up server