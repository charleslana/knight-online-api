package com.charles.knightonlineapi.model.mapper;

import com.charles.knightonlineapi.model.dto.UserItemBasicDTO;
import com.charles.knightonlineapi.model.dto.UserItemDTO;
import com.charles.knightonlineapi.model.entity.UserItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserItemMapper {

    UserItemDTO toDto(UserItemEntity entity);

    UserItemEntity toEntity(UserItemDTO dto);

    UserItemBasicDTO toBasicDto(UserItemEntity entity);

    UserItemEntity toEntity(UserItemBasicDTO dto);
}
