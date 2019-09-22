curl -H "Content-Type:application/json" -X PUT \
     --data '{"orderId":"1","accountId":"1","amount":"10.00"}' \
     http://localhost:8081/pay