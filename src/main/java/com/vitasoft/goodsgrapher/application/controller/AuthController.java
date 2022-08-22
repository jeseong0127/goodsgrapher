package com.vitasoft.goodsgrapher.application.controller;

import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @ApiOperation("test")
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "test";
    }
}
