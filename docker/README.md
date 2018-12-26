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

### Reference 
* https://github.com/spotify/docker-kafka/tree/master/kafka
