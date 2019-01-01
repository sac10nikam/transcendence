# Transcendence
A Generic Web Crawler.

## Services

|Port|Service Id|Service Name|
|:--:|:--------:|:----------:|
|8761|service-registry|Service Registry|
|9100-9109|---|Infrastructure|
|9100|gateway|Gateway Server|
|9101|config|Configuration Server|
|9110|zhihu-api|Zhihu API|
|9810|hub-topic|Topic Hub|
|9820|hub-people|People Hub|
|9830|hub-deed|Deed Hub|
|9900|api-executor|API Request Executor|

## How to Start
1. Start Kafka Service
  ```bash
   docker run -d \
     -p 2181:2181 \
     -p 9092:9092 \
     --name kafka \
     --env ADVERTISED_HOST=localhost \
     --env ADVERTISED_PORT=9092 \
     spotify/kafka
  ```


