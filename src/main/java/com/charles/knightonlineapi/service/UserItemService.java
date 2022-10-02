package com.charles.knightonlineapi.service;

import com.charles.knightonlineapi.exception.BusinessRuleException;
import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.model.dto.UserItemBasicDTO;
import com.charles.knightonlineapi.model.dto.UserItemDTO;
import com.charles.knightonlineapi.model.entity.ItemEntity;
import com.charles.knightonlineapi.model.entity.UserEntity;
import com.charles.knightonlineapi.model.entity.UserItemEntity;
import com.charles.knightonlineapi.model.mapper.UserItemMapper;
import com.charles.knightonlineapi.repository.UserItemRepository;
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
public class UserItemService {

    private final UserItemRepository repository;
    private final UserItemMapper mapper;
    private final MessageSource ms;
    private final UserService userService;
    private final ItemService itemService;

    @Transactional
    public ResponseDTO save(UserItemDTO dto) {
        UserEntity user = userService.getById(dto.getUserId());
        ItemEntity item = itemService.getById(dto.getItemId());
        UserItemEntity entity = mapper.toEntity(dto);
        entity.setUser(user);
        entity.setItem(item);
        repository.save(entity);
        return new ResponseDTO(MessageUtils.USER_ITEM_SUCCESS, "user.item.created", ms);
    }

    public UserItemBasicDTO get(Long id) {
        return repository.findByIdAndUserId(id, userService.getAuthAccount().getId()).map(mapper::toBasicDto).orElseThrow(() -> new BusinessRuleException(MessageUtils.USER_ITEM_EXCEPTION, "user.item.not.found"));
    }

    public Page<UserItemBasicDTO> getAll(Integer page, Integer size) {
        size = FunctionUtils.validatePageSize(size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return repository.findAllAndUserId(userService.getAuthAccount().getId(), pageRequest).map(mapper::toBasicDto);
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        repository.delete(getById(id));
        return new ResponseDTO(MessageUtils.USER_ITEM_SUCCESS, "user.item.deleted", ms);
    }

    private UserItemEntity getById(Long id) {
        return repository.findByIdAndUserId(id, userService.getAuthAccount().getId()).orElseThrow(() -> new BusinessRuleException(MessageUtils.USER_ITEM_EXCEPTION, "user.item.not.found"));
    }
}
