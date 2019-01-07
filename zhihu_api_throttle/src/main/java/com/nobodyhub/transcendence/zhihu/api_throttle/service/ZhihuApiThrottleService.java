package com.nobodyhub.transcendence.zhihu.api_throttle.service;

import com.nobodyhub.transcendence.api.common.message.ApiChannelBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(ZhihuApiChannel.class)
public class ZhihuApiThrottleService extends ApiChannelBaseService<ZhihuApiChannel> {

}
