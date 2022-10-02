package com.charles.knightonlineapi.repository;

import com.charles.knightonlineapi.model.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("select (count(i) > 0) from ItemEntity i where upper(i.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("FROM ItemEntity i where lower(i.name) like %:searchTerm%")
    Page<ItemEntity> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
