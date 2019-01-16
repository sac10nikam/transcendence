# Transcendence
A Generic Web Crawler.

## Services

|Port|Service Id|Service Name|
|:--:|:--------:|:----------:|
|8761|service-registry|Service Registry|
|9100-9109|---|Infrastructure|
|9100|gateway|Gateway Server|
|9101|config|Configuration Server|
|9200-9299|---|API Service|
|9210|zhihu-api|Zhihu API|
|9300-9399|---|API Service|
|9310|zhihu-api-throttle|Zhihu API Throttle|
|9810|hub-topic|Topic Hub|
|9820|hub-people|People Hub|
|9830|hub-deed|Deed Hub|
|9840|hub-tag|Tag Hub|
|9900|api-executor|API Request Executor|

## Category
The information collected is classified into 3 categories, topic, people, and deed.
**Topic**: a subject or matter towards which people can express their opinions.
**People**: the individual that performs independently on expressing their opinions.
**Deed**: the verbal actions of each individual showing their attitudes towards topics. 
**Tag**: a label added to Topic/People/Deed for classification.  

### Topic
* Zhihu Question(知乎问题)
* Zhihu Column(知乎专栏)

### People
* Zhihu Member(知乎会员)

### Deed
* Zhihu Answer(知乎回答)
* Zhihu Comment(知乎评论)
* Zhihu Article(知乎专栏文章)

### Tag
* Zhihu Topic(知乎话题)


## How to Start
1. Start Kafka Service
  ```bash
   docker run -d --network host --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 transcendence/kafka
  ```
1. Start [Service Registry Server](/infra_service_registry)
1. Start [Config Server](/config_server)
1. Start [Api Request Executor Server](/api_executor)
1. Start Hub Servers([Topic Hub](hub_topic), [People Hub](hub_people), [Deed Hub](hub_deed)) (Just in case after starting the Api Servers, they start to deal with the unprocessed messages and try to save to Hub Servers)
1. Start Api Servers([Zhihu API Server](/zhihu_api)&[Azhihu API Throttle Server](/zhihu_api_throttle)) 
1. Start [Gateway Server](/infra_gateway)


