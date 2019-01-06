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

## Category
### Topic
* Zhihu Topic
* Zhihu Question
* Zhihu Column

### People
* Zhihu Member

### Deed
* Zhihu Answer
* Zhihu Comment
* Zhihu Article

## How to Start
1. Start Kafka Service
  ```bash
   docker run -d --network host --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 transcendence/kafka
  ```
1. Start [Service Registry Server](/infra_service_registry)
1. Start [Api Request Executor Server](/api_executor)
1. Start Hub Servers([Topic Hub](hub_topic), [People Hub](hub_people), [Deed Hub](hub_deed)) (Just in case after starting the Api Servers, they start to deal with the unprocessed messages and try to save to Hub Servers)
1. Start Api Servers([Zhihu API Server](/zhihu_api))
1. Start [Gateway Server](/infra_gateway)


