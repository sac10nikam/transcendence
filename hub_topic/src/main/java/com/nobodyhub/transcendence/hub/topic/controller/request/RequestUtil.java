package com.nobodyhub.transcendence.hub.topic.controller.request;

import com.nobodyhub.transcendence.hub.topic.controller.paging.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class that extra information from incoming HTTP requset
 */
public final class RequestUtil {
    private RequestUtil() {
    }

    public static String getReqUrl(HttpServletRequest request) {
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }

    public static Paging getPaging(HttpServletRequest request, Page<?> page) {
        Pageable pageable = page.getPageable();
        return new Paging(page.isFirst(),
            page.isLast(),
            String.format("%s?page=%d&size=%d", request.getRequestURL().toString(), pageable.getPageNumber() - 1, pageable.getPageSize()),
            String.format("%s?page=%d&size=%d", request.getRequestURL().toString(), pageable.getPageNumber() + 1, pageable.getPageSize()));
    }
}
