package com.vitasoft.goodsgrapher.domain.model.kipris.repository;

import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
}
