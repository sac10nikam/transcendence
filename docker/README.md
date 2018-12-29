# Dockers for Development

## MongoDB
start server
```bash
sudo service mongod start
```


## [Kafka](kafka) 
A Kafka Server, incl. Zookeeper.

## How to Build

```bash
docker build -t transcendence/kafka kafka/
```

## How to Run
* Please make sure the the `ADVERTISED_HOST` is the correct the hostname
* Must use host network rather than just expose ports
```bash
docker run -d --network host --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 transcendence/kafka
```

## Miscellanenous
* Check message of topic
```bash
sudo docker exec -it <CONTAINER_ID> /bin/bash
/opt/kafka_2.11-1.0.1/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <TOPIC> --from-beginning --group <CONSUMER_GROUP>-monitor
```

### Reference 
* https://github.com/spotify/docker-kafka/tree/master/kafka
