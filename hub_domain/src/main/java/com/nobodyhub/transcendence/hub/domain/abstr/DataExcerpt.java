package com.nobodyhub.transcendence.hub.domain.abstr;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

/**
 * An excerpt of data that used for retrieving from database
 *
 * @param <T> the type enumeration of target data
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DataExcerpt<T> {

    /**
     * type of data
     */
    @NonNull
    protected T type;

    /**
     * the unique for data of given {@link #type}
     */
    @NonNull
    protected String id;
}
