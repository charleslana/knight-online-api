package com.charles.knightonlineapi.repository;

import com.charles.knightonlineapi.model.entity.UserItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserItemRepository extends JpaRepository<UserItemEntity, Long> {

    @Query("select u from UserItemEntity u where u.user.id = ?1")
    Page<UserItemEntity> findAllAndUserId(Long userId, Pageable pageable);

    @Query("select u from UserItemEntity u where u.id = ?1 and u.user.id = ?2")
    Optional<UserItemEntity> findByIdAndUserId(Long id, Long userId);
}
