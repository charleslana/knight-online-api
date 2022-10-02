package com.charles.knightonlineapi.service.mock;

import com.charles.knightonlineapi.model.dto.ItemDTO;
import com.charles.knightonlineapi.model.entity.ItemEntity;
import com.charles.knightonlineapi.model.mapper.ItemMapper;
import com.charles.knightonlineapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemMock {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ItemMapper mapper;

    public void createItem(ItemDTO dto) {
        ItemEntity entity = mapper.toEntity(dto);
        repository.save(entity);
    }

    public ItemDTO getItemMock(String randomString) {
        ItemDTO dto = new ItemDTO();
        dto.setName(randomString);
        dto.setImage(randomString);
        return dto;
    }

    public void deleteAllItem() {
        repository.deleteAll();
    }

    public Long getItem() {
        Optional<ItemEntity> first = repository.findAll().stream().findFirst();
        if (first.isEmpty()) {
            return 0L;
        }
        return first.get().getId();
    }
}
