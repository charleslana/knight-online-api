package com.charles.knightonlineapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserHeroDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    @Min(1)
    private Long heroId;
}
