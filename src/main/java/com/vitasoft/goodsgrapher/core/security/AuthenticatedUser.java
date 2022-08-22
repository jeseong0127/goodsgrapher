package com.vitasoft.goodsgrapher.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticatedUser {
    private final int userId;

    private final String userRole;
}
