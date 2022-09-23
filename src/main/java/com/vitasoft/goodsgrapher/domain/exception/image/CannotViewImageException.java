package com.vitasoft.goodsgrapher.domain.exception.image;

public class CannotViewImageException extends RuntimeException {
    public CannotViewImageException(String fileName) {
        super("이미지를 조회하는데 문제가 발생했습니다.\n" +
                "fileName: " + fileName);
    }
}
