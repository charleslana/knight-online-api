package com.charles.knightonlineapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ItemBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String image;
    private Long life;
    private Long armor;
    private Long damage;
    private Integer criticalRate;
    private Integer criticalDamage;
}
