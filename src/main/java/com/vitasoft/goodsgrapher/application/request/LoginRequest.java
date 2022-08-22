package com.vitasoft.goodsgrapher.application.request;

import com.sun.istack.NotNull;
import com.vitasoft.goodsgrapher.domain.model.enums.ProviderType;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {
    @ApiModelProperty(example = "NAVER")
    @NotNull
    private final ProviderType providerType;

    @ApiModelProperty(example = "010-0000-0000")
    @NotNull
    private final String phone;
}
