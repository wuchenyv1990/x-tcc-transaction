#!/usr/bin/env bash

function pay()
{
  curl -H "Content-Type:application/json" -X PUT \
     --data '{"orderId":"1","accountId":"1","amount":"10.00"}' \
     http://localhost:8081/pay
}

if [ -z "$1" ]; then
  times=0;
else
  times=$1
fi

for i in $(seq 0 "${times}"); do
  sleep "$i"
  pay
done

