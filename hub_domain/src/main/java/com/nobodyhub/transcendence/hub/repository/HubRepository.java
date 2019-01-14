package com.nobodyhub.transcendence.hub.repository;

import java.util.Optional;

public interface HubRepository<T, TYPE> {
    /**
     * Find the data with given id and of given type
     *
     * @param id   unqiue id of given detail type
     * @param type target detail type
     * @return
     */
    @Deprecated
    Optional<T> findFirstByDataIdAndType(String id, TYPE type);
}
