package com.nobodyhub.transcendence.mongodb.domain;

import com.nobodyhub.transcendence.mongodb.domain.merge.Mergeable;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class MongoObject<T extends Mergeable> {
    /**
     * mongodb object id, primary key
     */
    @Id
    private ObjectId objectId;

    /**
     * when the {@link #data} last updated/fetched from API
     */
    @Indexed
    @LastModifiedDate
    private DateTime updatedAt;

    /**
     * The raw data fetched from API
     */
    private T data;
}
