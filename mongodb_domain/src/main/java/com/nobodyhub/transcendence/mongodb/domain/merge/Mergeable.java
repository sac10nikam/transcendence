package com.nobodyhub.transcendence.mongodb.domain.merge;

/**
 * Mark target class as Zhihu Object for further
 *
 * <b>Note:</b>
 * - all subclass should not have fields of primitive type because the merge process depends on the null value
 * - all subclass need to have a non-argument constructor
 */
public interface Mergeable {
}
