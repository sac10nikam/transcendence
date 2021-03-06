package com.nobodyhub.transcendence.hub.deed.repository;

import com.nobodyhub.transcendence.hub.domain.Deed;
import com.nobodyhub.transcendence.hub.domain.excerpt.DeedDataExcerpt;
import com.nobodyhub.transcendence.hub.repository.HubRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface DeedRepository extends PagingAndSortingRepository<Deed, String>, HubRepository<Deed, DeedDataExcerpt> {

    /**
     * find the deeds belong to topic with given name
     *
     * @param name
     * @param pageable
     * @return a list of Deed sorted {@link Deed#getCreatedAt()} descendingly
     */
    List<Deed> findByTopic_NameOrderByCreatedAtDesc(String name, Pageable pageable);

    /**
     * find the deeds belong to people with given name
     *
     * @param name
     * @param pageable
     * @return a list of Deed sorted {@link Deed#getCreatedAt()} descendingly
     */
    List<Deed> findByPeople_NameOrderByCreatedAtDesc(String name, Pageable pageable);

    /**
     * find the deeds that belongs to the given parent id
     *
     * @param parentId
     * @return
     */
    List<Deed> findByParentIdOrderByCreatedAt(String parentId, Pageable pageable);
}
