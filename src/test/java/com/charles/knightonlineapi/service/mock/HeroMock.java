package com.charles.knightonlineapi.service.mock;

import com.charles.knightonlineapi.model.dto.HeroDTO;
import com.charles.knightonlineapi.model.entity.HeroEntity;
import com.charles.knightonlineapi.model.mapper.HeroMapper;
import com.charles.knightonlineapi.repository.HeroRepository;
import com.charles.knightonlineapi.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HeroMock {

    @Autowired
    private HeroRepository repository;

    @Autowired
    private HeroMapper mapper;

    public void createHero(HeroDTO dto) {
        HeroEntity entity = mapper.toEntity(dto);
        repository.save(entity);
    }

    public HeroDTO getHeroMock(String randomString) {
        HeroDTO dto = new HeroDTO();
        dto.setName(randomString);
        dto.setImage(randomString);
        return dto;
    }

    public void deleteAllHero() {
        repository.deleteAll();
    }

    public Long getHero() {
        Optional<HeroEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
