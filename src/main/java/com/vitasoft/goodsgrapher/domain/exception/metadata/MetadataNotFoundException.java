package com.vitasoft.goodsgrapher.domain.exception.metadata;

public class MetadataNotFoundException extends RuntimeException {
    public MetadataNotFoundException(int metaSeq) {
        super("metaSeq : " + metaSeq + "\n" +
                " metadata 존재하지 않습니다.");
    }
}
