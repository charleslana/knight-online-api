package com.charles.knightonlineapi.service.mock;

import com.charles.knightonlineapi.model.dto.UserHeroDTO;
import com.charles.knightonlineapi.model.entity.HeroEntity;
import com.charles.knightonlineapi.model.entity.UserEntity;
import com.charles.knightonlineapi.model.entity.UserHeroEntity;
import com.charles.knightonlineapi.model.mapper.UserHeroMapper;
import com.charles.knightonlineapi.repository.UserHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHeroMock {

    @Autowired
    private UserHeroRepository repository;

    @Autowired
    private UserHeroMapper mapper;

    public void createUserHero(UserHeroDTO dto) {
        UserHeroEntity entity = mapper.toEntity(dto);
        UserEntity user = new UserEntity();
        user.setId(dto.getUserId());
        entity.setUser(user);
        HeroEntity hero = new HeroEntity();
        hero.setId(dto.getHeroId());
        entity.setHero(hero);
        repository.save(entity);
    }

    public UserHeroDTO getUserHeroMock(Long userId, Long heroId) {
        UserHeroDTO dto = new UserHeroDTO();
        dto.setUserId(userId);
        dto.setHeroId(heroId);
        return dto;
    }

    public void deleteAllUserHero() {
        repository.deleteAll();
    }

    public Long getUserHero() {
        Optional<UserHeroEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
