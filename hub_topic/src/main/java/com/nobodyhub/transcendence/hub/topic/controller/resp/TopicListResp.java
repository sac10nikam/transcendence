package com.nobodyhub.transcendence.hub.topic.controller.resp;

import com.nobodyhub.transcendence.hub.topic.controller.paging.Paging;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Response that contains a list of Topic data and Paging information(if necessary)
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TopicListResp<T> {
    private final Paging paging;
    private final List<T> contents;

    public static <T> TopicListResp of(Paging paging, List<T> contents) {
        return new TopicListResp<>(paging, contents);
    }
}
