package com.vitasoft.goodsgrapher.domain.model.kipris.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "com_article")
@Getter
@Setter
@DynamicUpdate
@RequiredArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;

    private String boardName;

    private String title;

    private String content;

    private char isDeleted;

    private String regId;

    private String regName;

    private LocalDateTime regDate;

    private int viewCount;

    private String answer;

    private String ansId;

    private String ansName;

    private LocalDateTime ansDate;

    private char ansChk;

    private String ansType;

    private String ansTypeName;
}
