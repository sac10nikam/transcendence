package com.nobodyhub.transcendence.mongodb.domain;

import com.nobodyhub.transcendence.common.merge.Mergeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@EqualsAndHashCode
public abstract class MongoObject<T extends Mergeable> {
    /**
     * mongodb object id, primary key
     */
    @Id
    @Getter
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
    protected T data;

    /**
     * Update the data in the object
     * Usually contains updating of {@link #data} and other other necessary fields
     *
     * @param data
     */
    public abstract void setData(T data);
}
