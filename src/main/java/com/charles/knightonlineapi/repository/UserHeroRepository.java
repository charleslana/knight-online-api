package com.charles.knightonlineapi.repository;

import com.charles.knightonlineapi.model.entity.UserHeroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHeroRepository extends JpaRepository<UserHeroEntity, Long> {

    @Query("select count(u) from UserHeroEntity u where u.user.id = ?1 and u.hero.id = ?2")
    Long countByUserIdAndHeroId(Long userId, Long heroId);

    @Query("select u from UserHeroEntity u where u.user.id = ?1")
    Page<UserHeroEntity> findAllAndUserId(Long userId, Pageable pageable);

    @Query("select u from UserHeroEntity u where u.id = ?1 and u.user.id = ?2")
    Optional<UserHeroEntity> findByIdAndUserId(Long id, Long userId);
}
