#!/bin/bash

set -e # 오류 발생 시 스크립트 종료

DOCKER_APP_NAME=rangers

# 현재 실행 중인 컨테이너 확인
EXIST_BLUE=$(docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps -q)

if [ -n "$EXIST_BLUE" ]; then
    echo "Blue container is already running."
    docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build
    sudo cp ./nginx.green.conf /etc/nginx/nginx.conf
    BEFORE_COMPOSE_COLOR="blue"
    AFTER_COMPOSE_COLOR="green"
else
    echo "Starting Blue..."
    docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build
    sudo cp ./nginx.blue.conf /etc/nginx/nginx.conf
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
fi

sleep 10

# Nginx 리로드
sudo systemctl reload nginx

# 이전 컨테이너 종료
docker-compose -p ${DOCKER_APP_NAME}-${BEFORE_COMPOSE_COLOR} -f docker-compose.${BEFORE_COMPOSE_COLOR}.yml down
