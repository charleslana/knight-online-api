package com.charles.knightonlineapi.service;

import com.charles.knightonlineapi.exception.BusinessRuleException;
import com.charles.knightonlineapi.model.dto.HeroBasicDTO;
import com.charles.knightonlineapi.model.dto.HeroDTO;
import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.model.entity.HeroEntity;
import com.charles.knightonlineapi.model.mapper.HeroMapper;
import com.charles.knightonlineapi.repository.HeroRepository;
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

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HeroService {

    private final HeroRepository repository;
    private final HeroMapper mapper;
    private final MessageSource ms;

    @Transactional
    public ResponseDTO save(HeroDTO dto) {
        validateExistsName(dto);
        HeroEntity entity = mapper.toEntity(dto);
        repository.save(entity);
        return new ResponseDTO(MessageUtils.HERO_SUCCESS, "hero.created", ms);
    }

    @Transactional
    public ResponseDTO update(HeroDTO dto) {
        HeroEntity entity = getById(dto.getId());
        validateExistsName(dto, entity);
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        return new ResponseDTO(MessageUtils.HERO_SUCCESS, "hero.updated", ms);
    }

    public HeroBasicDTO get(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> new BusinessRuleException(MessageUtils.HERO_EXCEPTION, "hero.not.found"));
    }

    public Page<HeroBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.findAll(pageRequest).map(mapper::toBasicDto);
    }

    public Page<HeroBasicDTO> search(String searchTerm, Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.search(searchTerm.toLowerCase(), pageRequest).map(mapper::toBasicDto);
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        repository.delete(getById(id));
        return new ResponseDTO(MessageUtils.HERO_SUCCESS, "hero.deleted", ms);
    }

    private HeroEntity getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessRuleException(MessageUtils.HERO_EXCEPTION, "hero.not.found"));
    }

    private void validateExistsName(HeroDTO dto) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName().trim());
        if (existsByName) {
            throw new BusinessRuleException(MessageUtils.USER_EXCEPTION, "hero.exists.name");
        }
    }

    private void validateExistsName(HeroDTO dto, HeroEntity entity) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName().trim());
        if (!Objects.equals(entity.getName(), dto.getName().trim()) && existsByName) {
            throw new BusinessRuleException(MessageUtils.USER_EXCEPTION, "hero.exists.name");
        }
    }
}
