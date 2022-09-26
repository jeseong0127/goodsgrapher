package com.vitasoft.goodsgrapher.domain.model.kipris.repository;

import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFileRepository extends JpaRepository<ArticleFile, Integer> {
    List<ArticleFile> findAllByArticleIdAndIsDeletedAndRegId(int metaSeq, String isDeleted, String regId);

    ArticleFile findTopByArticleIdOrderByArticleFileIdDesc(int metaSeq);

    int countByArticleIdAndRegId(int metaSeq, String regId);
}
