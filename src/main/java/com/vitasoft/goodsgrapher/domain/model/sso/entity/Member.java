package com.vitasoft.goodsgrapher.domain.model.sso.entity;

import com.vitasoft.goodsgrapher.domain.model.enums.MemberRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "com_members")
@Getter
@RequiredArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo = null;

    private String memberId;

    private String memberName;

    private String memberPhone;

    private String memberEmail;

    private String memberEmail2;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private final MemberRole memberRole = MemberRole.ROLE001;
}
