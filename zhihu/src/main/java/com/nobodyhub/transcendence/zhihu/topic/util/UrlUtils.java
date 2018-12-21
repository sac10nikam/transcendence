package com.nobodyhub.transcendence.zhihu.topic.util;

import org.springframework.web.util.UriUtils;

import static com.google.common.base.Charsets.UTF_8;

public final class UrlUtils {
    private UrlUtils() {
    }

    /**
     * Helper method to handle the url
     * - decode the url if encoded
     * - remove the protocol
     * - remote the host
     *
     * @param url
     * @return
     */
    public static String convert(String url) {
        return UriUtils.decode(url, UTF_8)
            .replaceFirst("^(http:|https:)//", "")
            .replaceFirst("www.zhihu.com", "");
    }
}
