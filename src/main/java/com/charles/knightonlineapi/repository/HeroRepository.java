package com.charles.knightonlineapi.repository;

import com.charles.knightonlineapi.model.entity.HeroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {

    @Query("select (count(h) > 0) from HeroEntity h where upper(h.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("FROM HeroEntity h where lower(h.name) like %:searchTerm%")
    Page<HeroEntity> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
