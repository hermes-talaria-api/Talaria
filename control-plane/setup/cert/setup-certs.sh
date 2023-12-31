#!/bin/bash

if [ -f "./setup/cert/setup-conf.sh" ]; then
  . ./setup/cert/setup-conf.sh
fi

COMPOSE_NOT_SUPPORTED=$(command -v docker-compose 2> /dev/null)

if [ -z "$COMPOSE_NOT_SUPPORTED" ]; then
  DOCKER_COMPOSE_COMMAND="docker compose"
else
  DOCKER_COMPOSE_COMMAND="docker-compose"
fi

$DOCKER_COMPOSE_COMMAND -f ./docker-compose.setup.yml up -d

while true; do
  if [ ! "$(docker ps -q -f name=certbot)" ]; then
    break
  fi
  sleep 1
done

$DOCKER_COMPOSE_COMMAND -f ./docker-compose.setup.yml down