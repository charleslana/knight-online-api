package com.charles.knightonlineapi.service.mock;

import com.charles.knightonlineapi.model.dto.UserItemDTO;
import com.charles.knightonlineapi.model.entity.ItemEntity;
import com.charles.knightonlineapi.model.entity.UserEntity;
import com.charles.knightonlineapi.model.entity.UserItemEntity;
import com.charles.knightonlineapi.model.mapper.UserItemMapper;
import com.charles.knightonlineapi.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserItemMock {

    @Autowired
    private UserItemRepository repository;

    @Autowired
    private UserItemMapper mapper;

    public void createUserItem(UserItemDTO dto) {
        UserItemEntity entity = mapper.toEntity(dto);
        UserEntity user = new UserEntity();
        user.setId(dto.getUserId());
        entity.setUser(user);
        ItemEntity item = new ItemEntity();
        item.setId(dto.getItemId());
        entity.setItem(item);
        repository.save(entity);
    }

    public UserItemDTO getUserItemMock(Long userId, Long itemId) {
        UserItemDTO dto = new UserItemDTO();
        dto.setUserId(userId);
        dto.setItemId(itemId);
        return dto;
    }

    public void deleteAllUserItem() {
        repository.deleteAll();
    }

    public Long getUserItem() {
        Optional<UserItemEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
