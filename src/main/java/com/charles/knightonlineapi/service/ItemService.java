package com.charles.knightonlineapi.service;

import com.charles.knightonlineapi.exception.BusinessRuleException;
import com.charles.knightonlineapi.model.dto.ItemBasicDTO;
import com.charles.knightonlineapi.model.dto.ItemDTO;
import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.model.entity.ItemEntity;
import com.charles.knightonlineapi.model.mapper.ItemMapper;
import com.charles.knightonlineapi.repository.ItemRepository;
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
public class ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final MessageSource ms;

    @Transactional
    public ResponseDTO save(ItemDTO dto) {
        validateExistsName(dto);
        ItemEntity entity = mapper.toEntity(dto);
        repository.save(entity);
        return new ResponseDTO(MessageUtils.ITEM_SUCCESS, "item.created", ms);
    }

    @Transactional
    public ResponseDTO update(ItemDTO dto) {
        ItemEntity entity = getById(dto.getId());
        validateExistsName(dto, entity);
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        return new ResponseDTO(MessageUtils.ITEM_SUCCESS, "item.updated", ms);
    }

    public ItemBasicDTO get(Long id) {
        return repository.findById(id).map(mapper::toBasicDto).orElseThrow(() -> new BusinessRuleException(MessageUtils.ITEM_EXCEPTION, "item.not.found"));
    }

    public Page<ItemBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.findAll(pageRequest).map(mapper::toBasicDto);
    }

    public Page<ItemBasicDTO> search(String searchTerm, Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.search(searchTerm.toLowerCase(), pageRequest).map(mapper::toBasicDto);
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        repository.delete(getById(id));
        return new ResponseDTO(MessageUtils.ITEM_SUCCESS, "item.deleted", ms);
    }

    public ItemEntity getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessRuleException(MessageUtils.ITEM_EXCEPTION, "item.not.found"));
    }

    private void validateExistsName(ItemDTO dto) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName().trim());
        if (existsByName) {
            throw new BusinessRuleException(MessageUtils.ITEM_EXCEPTION, "item.exists.name");
        }
    }

    private void validateExistsName(ItemDTO dto, ItemEntity entity) {
        boolean existsByName = repository.existsByNameIgnoreCase(dto.getName().trim());
        if (!Objects.equals(entity.getName(), dto.getName().trim()) && existsByName) {
            throw new BusinessRuleException(MessageUtils.ITEM_EXCEPTION, "item.exists.name");
        }
    }
}
