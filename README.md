# Transcendence
A Generic Web Crawler.

## Services

|Port|Service Id|Service Name|Default Port|
|:--:|:--------:|:----------:|-----------:|
|8761|service-registry|Service Registry|8761|
|9100-9109|---|Infrastructure|---|
|9100|gateway|Gateway Server|9100|
|9101|config|Configuration Server|9101|
|9110-9119|zhihu-member|Zhihu Member Application|9110|
|9120-9129|zhihu-topic|Zhihu Topic Application|9120|

## How to Start
1. Start Kafka Service: `docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 spotify/kafka`


