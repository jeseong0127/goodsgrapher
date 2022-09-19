package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.model.dto.GetArticlesDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<GetArticlesDto> getArticles() {
        return articleRepository.findAll().stream()
                .map(GetArticlesDto::new)
                .collect(Collectors.toList());
    }
}
