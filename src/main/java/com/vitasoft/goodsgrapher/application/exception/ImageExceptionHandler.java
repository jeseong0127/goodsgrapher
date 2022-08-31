package com.vitasoft.goodsgrapher.application.exception;

import com.vitasoft.goodsgrapher.core.response.ErrorResponse;
import com.vitasoft.goodsgrapher.domain.exception.metadata.CannotUploadImageException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImageExceptionHandler {

    @ExceptionHandler(CannotUploadImageException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCannotUploadImage(CannotUploadImageException exception) {
        return new ErrorResponse(HttpStatus.CONFLICT, "Image-001", exception.getMessage());
    }
}
