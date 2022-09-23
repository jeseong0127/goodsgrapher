package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.model.dto.GetArticleDto;
import com.vitasoft.goodsgrapher.domain.model.dto.GetNoticeDto;
import com.vitasoft.goodsgrapher.domain.model.enums.BoardName;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<GetArticleDto> getArticles() {
        return articleRepository.findAllByBoardNameAndIsDeletedOrderByArticleIdDesc(BoardName.NOTICE, 'N').stream()
                .map(GetArticleDto::new)
                .collect(Collectors.toList());
    }

    public GetNoticeDto getLatestNotice() {
        return new GetNoticeDto(articleRepository.findTopByBoardNameAndIsDeletedOrderByArticleIdDesc(BoardName.NOTICE, 'N'));
    }
}
