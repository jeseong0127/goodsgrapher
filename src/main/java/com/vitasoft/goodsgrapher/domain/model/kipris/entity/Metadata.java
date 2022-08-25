package com.vitasoft.goodsgrapher.domain.model.kipris.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "kps_metadata")
@Getter
@RequiredArgsConstructor
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metaSeq;

    private String productCategory;

    private String articleName;

    private String modelName;

    private String companyName;

    private String registrationNumber;

    private String applicationNumber;

    private String reservId;

    private String regId;

    private int imgCount;
}
