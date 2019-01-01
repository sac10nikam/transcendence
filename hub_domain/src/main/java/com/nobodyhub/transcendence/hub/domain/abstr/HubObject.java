package com.nobodyhub.transcendence.hub.domain.abstr;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public abstract class HubObject {
    /**
     * last update via api
     */
    @Indexed
    @LastModifiedDate
    private DateTime lastUpdatedViaApi;
}
