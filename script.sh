#!/bin/bash
docker-compose build
printf "\nDocker has successfully build user network, containers and associated volumes!\n\n"
docker-compose up
printf "\nDocker has successfully executed the server and client files\n"