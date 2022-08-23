package com.vitasoft.goodsgrapher.application.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {

    @ApiModelProperty(example = "2187759674")
    @NotNull
    private final String providerId;

    @ApiModelProperty(example = "010-0000-0000")
    @NotNull
    private final String phone;
}
