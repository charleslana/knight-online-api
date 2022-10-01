package com.charles.knightonlineapi.model.mapper;

import com.charles.knightonlineapi.model.dto.UserHeroBasicDTO;
import com.charles.knightonlineapi.model.dto.UserHeroDTO;
import com.charles.knightonlineapi.model.entity.UserHeroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserHeroMapper {

    UserHeroDTO toDto(UserHeroEntity entity);

    UserHeroEntity toEntity(UserHeroDTO dto);

    UserHeroBasicDTO toBasicDto(UserHeroEntity entity);

    UserHeroEntity toEntity(UserHeroBasicDTO dto);
}
