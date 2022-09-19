package com.vitasoft.goodsgrapher.domain.model.kipris.repository;

import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
    List<Metadata> findAllByImgCountLessThan(int imgCount);

    int countByReserveId(String reserveId);

    List<Metadata> findAllByReserveIdOrRegId(String reserveId, String regId);

    Optional<Metadata> findByMetaSeqAndRegId(int metaSeq, String memberId);
}
