package com.nobodyhub.transcendence.hub.domain.excerpt;

import com.nobodyhub.transcendence.common.domain.DataExcerpt;
import com.nobodyhub.transcendence.hub.domain.Deed;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * An excerpt of topic data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeedDataExcerpt extends DataExcerpt<Deed.DeedType> {

    private DeedDataExcerpt(Deed.DeedType type, String id) {
        super(type, id);
    }

    public static DeedDataExcerpt of(Deed.DeedType type, String id) {
        return new DeedDataExcerpt(type, id);
    }
}
