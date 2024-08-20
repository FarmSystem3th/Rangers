#!/bin/bash

# 현재 실행중인 App이 green인지 확인합니다.
IS_GREEN=$(docker ps --format '{{.Names}}' | grep -w green)
DEFAULT_CONF="/etc/nginx/nginx.conf"

# blue가 실행중이라면 green을 up합니다.
if [ -z "$IS_GREEN" ]; then

  echo "### BLUE => GREEN ###"

  echo ">>> 1. green image를 pull합니다."
  docker compose pull green

  echo ">>> 2. green container를 up합니다."
  docker compose up -d green

  while true; do
    echo ">>> 3. green health check 중..."
    sleep 3
    REQUEST=$(curl -s http://127.0.0.1:8092) # green으로 request
    if [ -n "$REQUEST" ]; then
      echo "🧁health check success!!!"
      break
    fi
  done

  echo ">>> 4. nginx를 다시 실행합니다."
  sudo cp /etc/nginx/green-nginx.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo ">>> 5. blue container를 down합니다."
  docker compose stop blue

# green이 실행중이면 blue를 up합니다.
else
  echo "### GREEN => BLUE ###"

  echo ">>> 1. blue image를 pull합니다."
  docker compose pull blue

  echo ">>> 2. blue container up합니다."
  docker compose up -d blue

  while true; do
    echo ">>> 3. blue health check 중..."
    sleep 3
    REQUEST=$(curl -s http://127.0.0.1:8091) # blue로 request
    if [ -n "$REQUEST" ]; then
      echo "🧁health check success!!!"
      break
    fi
  done

  echo ">>> 4. nginx를 다시 실행합니다."
  sudo cp /etc/nginx/blue-nginx.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo ">>> 5. green container를 down합니다."
  docker compose stop green
fi