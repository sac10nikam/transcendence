package com.nobodyhub.transcendence.hub.domain.excerpt;

import com.nobodyhub.transcendence.common.domain.DataExcerpt;
import com.nobodyhub.transcendence.hub.domain.People;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * An excerpt of people data
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PeopleDataExcerpt extends DataExcerpt<People.PeopleType> {

    private PeopleDataExcerpt(People.PeopleType type, String id) {
        super(type, id);
    }

    public static PeopleDataExcerpt of(People.PeopleType type, String id) {
        return new PeopleDataExcerpt(type, id);
    }
}
