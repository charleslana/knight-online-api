package com.charles.knightonlineapi.service;

import com.charles.knightonlineapi.exception.BusinessRuleException;
import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.model.dto.UserHeroBasicDTO;
import com.charles.knightonlineapi.model.dto.UserHeroDTO;
import com.charles.knightonlineapi.model.entity.HeroEntity;
import com.charles.knightonlineapi.model.entity.UserEntity;
import com.charles.knightonlineapi.model.entity.UserHeroEntity;
import com.charles.knightonlineapi.model.mapper.UserHeroMapper;
import com.charles.knightonlineapi.repository.UserHeroRepository;
import com.charles.knightonlineapi.utils.FunctionUtils;
import com.charles.knightonlineapi.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserHeroService {

    private final UserHeroRepository repository;
    private final UserHeroMapper mapper;
    private final MessageSource ms;
    private final UserService userService;
    private final HeroService heroService;

    @Transactional
    public ResponseDTO save(UserHeroDTO dto) {
        UserEntity user = userService.getById(dto.getUserId());
        HeroEntity hero = heroService.getById(dto.getHeroId());
        validateHeroExist(dto);
        UserHeroEntity entity = mapper.toEntity(dto);
        entity.setUser(user);
        entity.setHero(hero);
        repository.save(entity);
        return new ResponseDTO(MessageUtils.USER_HERO_SUCCESS, "user.hero.created", ms);
    }

    public UserHeroBasicDTO get(Long id) {
        return repository.findByIdAndUserId(id, userService.getAuthAccount().getId()).map(mapper::toBasicDto).orElseThrow(() -> new BusinessRuleException(MessageUtils.USER_HERO_EXCEPTION, "user.hero.not.found"));
    }

    public Page<UserHeroBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.findAllAndUserId(userService.getAuthAccount().getId(), pageRequest).map(mapper::toBasicDto);
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        repository.delete(getById(id));
        return new ResponseDTO(MessageUtils.USER_HERO_SUCCESS, "user.hero.deleted", ms);
    }

    private UserHeroEntity getById(Long id) {
        return repository.findByIdAndUserId(id, userService.getAuthAccount().getId()).orElseThrow(() -> new BusinessRuleException(MessageUtils.USER_HERO_EXCEPTION, "user.hero.not.found"));
    }

    private void validateHeroExist(UserHeroDTO dto) {
        if (repository.countByUserIdAndHeroId(dto.getUserId(), dto.getHeroId()) >= 1) {
            throw new BusinessRuleException(MessageUtils.USER_HERO_EXCEPTION, "user.hero.count.exist");
        }
    }
}
