package com.charles.knightonlineapi.model.mapper;

import com.charles.knightonlineapi.model.dto.ItemBasicDTO;
import com.charles.knightonlineapi.model.dto.ItemDTO;
import com.charles.knightonlineapi.model.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDTO toDto(ItemEntity entity);

    ItemEntity toEntity(ItemDTO dto);

    ItemBasicDTO toBasicDto(ItemEntity entity);

    ItemEntity toEntity(ItemBasicDTO dto);
}
