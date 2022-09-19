package com.vitasoft.goodsgrapher.domain.model.dto;

import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Article;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GetArticlesDto {
    private final int articleId;
    private final String boardName;
    private final String title;
    private final String content;
    private final char isDeleted;
    private final String regId;
    private final String regName;
    private final LocalDateTime regDate;

    private final int viewCount;
    private final String answer;
    private final String ansId;
    private final String ansName;
    private final LocalDateTime ansDate;
    private final char ansChk;
    private final String ansType;
    private final String ansTypeName;

    public GetArticlesDto(Article article) {
        this.articleId = article.getArticleId();
        this.boardName = article.getBoardName();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.isDeleted = article.getIsDeleted();
        this.regId = article.getRegId();
        this.regName = article.getRegName();
        this.regDate = article.getRegDate();

        this.viewCount = article.getViewCount();
        this.answer = article.getAnswer();
        this.ansId = article.getAnsId();
        this.ansName = article.getAnsName();
        this.ansDate = article.getAnsDate();
        this.ansChk = article.getAnsChk();
        this.ansType = article.getAnsType();
        this.ansTypeName = article.getAnsTypeName();
    }
}
