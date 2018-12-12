package com.nobodyhub.transcendence.zhihu.api.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class ApiRequestServiceImpl implements ApiRequestService {
    private final RestTemplate restTemplate;

    public ApiRequestServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> Optional<T> doGet(String url, Class<T> clz, Object... urlVariables) {
        try {
            log.info("Accessing : [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, urlVariables));
            return Optional.ofNullable(this.restTemplate.getForObject(url, clz, urlVariables));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error when access URL: [{}]",
                this.restTemplate.getUriTemplateHandler().expand(url, urlVariables));
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}
