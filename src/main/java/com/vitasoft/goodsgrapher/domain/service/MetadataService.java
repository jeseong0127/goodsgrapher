package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.exception.metadata.ExceededReservedCountLimitException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.MetadataNotFoundException;
import com.vitasoft.goodsgrapher.domain.model.dto.GetMetadataDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.MetadataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetadataService {

    private final MetadataRepository metadataRepository;

    public List<GetMetadataDto> getMetadataList() {
        return metadataRepository.findAllByImgCountLessThan(62).stream()
                .map(GetMetadataDto::new)
                .collect(Collectors.toList());
    }

    public void reserveMetadata(String memberId, int metaSeq) {
        int reservedCount = metadataRepository.countByReserveId(memberId);

        if (reservedCount > 3)
            throw new ExceededReservedCountLimitException();

        Metadata metadata = metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq));
        metadata.setReserveId(memberId);
        metadata.setReserveDate(LocalDateTime.now());
        metadataRepository.save(metadata);
    }

    public void cancelReserveMetadata(int metaSeq) {
        Metadata metadata = metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq));
        metadata.setReserveId("N/A");
        metadata.setReserveDate(null);
        metadataRepository.save(metadata);
    }
}
