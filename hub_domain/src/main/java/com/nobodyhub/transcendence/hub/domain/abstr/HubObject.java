package com.nobodyhub.transcendence.hub.domain.abstr;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Set;

@Data
@EqualsAndHashCode
public abstract class HubObject<T extends DataExcerpt> {
    /**
     * last update via api
     */
    @Indexed
    @LastModifiedDate
    private DateTime lastUpdatedViaApi;

    /**
     * A excerpt of data included
     */
    protected Set<T> excerpts;

    public void addExcerpt(T excerpt) {
        if (excerpts == null) {
            excerpts = Sets.newHashSet();
        }
        this.excerpts.add(excerpt);
    }
}
