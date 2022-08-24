package com.vitasoft.goodsgrapher.domain.model.dto;

import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;

import lombok.Getter;

@Getter
public class GetMetadataDto {
    private final int metaSeq;
    private final String productCategory;
    private final String articleName;
    private final String modelName;
    private final String companyName;
    private final String registrationNumber;
    private final String applicationNumber;
    private final String reserveId;
    private final String regId;

    public GetMetadataDto(Metadata metadata) {
        this.metaSeq = metadata.getMetaSeq();
        this.productCategory = metadata.getProductCategory();
        this.articleName = metadata.getArticleName();
        this.modelName = metadata.getModelName();
        this.companyName = metadata.getCompanyName();
        this.registrationNumber = metadata.getRegistrationNumber();
        this.applicationNumber = metadata.getApplicationNumber();
        this.reserveId = metadata.getReserveId();
        this.regId = metadata.getRegId();
    }
}
