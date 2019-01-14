package com.nobodyhub.transcendence.hub.repository;

import com.nobodyhub.transcendence.common.domain.DataExcerpt;

import java.util.Optional;

/**
 * Hub common repository
 *
 * @param <T> Entity Type
 * @param <E> Excerpt Type
 */
public interface HubRepository<T, E extends DataExcerpt> {
    /**
     * Find Topic by excerpt information
     *
     * @param excerpt
     * @return
     */
    Optional<T> findFirstByExcerptsContains(E excerpt);
}
