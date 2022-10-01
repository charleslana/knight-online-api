package com.charles.knightonlineapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserHeroBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer level;
    private Integer experience;
    private Integer enhanced;
    private HeroBasicDTO hero;
}
