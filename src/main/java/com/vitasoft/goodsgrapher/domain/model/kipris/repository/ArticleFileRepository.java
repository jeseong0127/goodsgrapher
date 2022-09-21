package com.vitasoft.goodsgrapher.domain.model.kipris.repository;

import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFileRepository extends JpaRepository<ArticleFile, Integer> {
    List<ArticleFile> findALlByArticleIdAndIsDeleted(int metaSeq, String isDeleted);

    ArticleFile findTopByArticleIdOrderByArticleFileIdDesc(int metaSeq);
}
