# Dockers for Development

## [Kafka](kafka) 
A Kafka Server, incl. Zookeeper.

## How to Build

```bash
docker build -t transcendence/kafka kafka/
```

## How to Run

```bash
docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 transcendence/kafka
```

### Reference 
* https://github.com/spotify/docker-kafka/tree/master/kafka
