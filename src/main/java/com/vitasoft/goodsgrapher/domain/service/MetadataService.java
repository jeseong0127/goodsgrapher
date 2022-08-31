package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.application.request.MetadataRequest;
import com.vitasoft.goodsgrapher.domain.exception.metadata.ExceededReservedCountLimitException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.MetadataNotFoundException;
import com.vitasoft.goodsgrapher.domain.model.dto.GetMetadataDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.ArticleFileRepository;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.MetadataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetadataService {

    private final ImageService imageService;

    private final ArticleFileRepository articleFileRepository;

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

    public GetMetadataDto getMetadataDetail(int metaSeq) {
        return new GetMetadataDto(metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq)));
    }

    public void uploadMetadata(String memberId, MetadataRequest metadataRequest) {
        int defaultImageCount = 62;

        for (int i = 0; i < metadataRequest.getImages().size(); i++) {
            ArticleFile articleFile = imageService.uploadMetadataImage(memberId, metadataRequest.getMetaSeq(), metadataRequest.getImages().get(i), i);
            articleFileRepository.save(articleFile);
        }

        Metadata metadata = metadataRepository.findById(metadataRequest.getMetaSeq()).orElseThrow(() -> new MetadataNotFoundException(metadataRequest.getMetaSeq()));
        metadata.setReserveId("N/A");
        metadata.setReserveDate(null);
        metadata.setRegId(memberId);
        metadata.setRegDate(LocalDateTime.now());
        metadata.setImgCount(metadata.getImgCount() + defaultImageCount);

        metadataRepository.save(metadata);
    }
}
