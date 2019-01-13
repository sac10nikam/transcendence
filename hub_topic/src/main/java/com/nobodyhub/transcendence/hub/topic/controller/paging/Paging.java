package com.nobodyhub.transcendence.hub.topic.controller.paging;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Paging {
    /**
     * Whether this is the first page
     */
    private final boolean first;
    /**
     * whether this is the last page
     */
    private final boolean last;
    /**
     * the link to the previous page
     * valid only if {@link #first} = false
     */
    private final String previous;
    /**
     * the link to the next page
     * valid only if {@link #last} = false
     */
    private final String next;

}
