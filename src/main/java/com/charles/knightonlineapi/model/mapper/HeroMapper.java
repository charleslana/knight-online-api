package com.charles.knightonlineapi.model.mapper;

import com.charles.knightonlineapi.model.dto.HeroBasicDTO;
import com.charles.knightonlineapi.model.dto.HeroDTO;
import com.charles.knightonlineapi.model.entity.HeroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    HeroDTO toDto(HeroEntity entity);

    HeroEntity toEntity(HeroDTO dto);

    HeroBasicDTO toBasicDto(HeroEntity entity);

    HeroEntity toEntity(HeroBasicDTO dto);
}
