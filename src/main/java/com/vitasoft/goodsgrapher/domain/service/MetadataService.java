package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.application.request.DeleteMetadataRequest;
import com.vitasoft.goodsgrapher.application.request.MetadataRequest;
import com.vitasoft.goodsgrapher.domain.exception.member.MemberNotFoundException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.ArticleFileNotFoundException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.ExceededReservedCountLimitException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.ExistsWorkedMetadataException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.MetadataNotFoundException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.RegIdIsNotWorkerException;
import com.vitasoft.goodsgrapher.domain.model.dto.GetArticleFileDto;
import com.vitasoft.goodsgrapher.domain.model.dto.GetMetadataDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.AdjustmentRepository;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.ArticleFileRepository;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.MetadataRepository;
import com.vitasoft.goodsgrapher.domain.model.sso.entity.Member;
import com.vitasoft.goodsgrapher.domain.model.sso.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    private final AdjustmentRepository adjustmentRepository;

    private final MemberRepository memberRepository;

    String defaultReserveId = "N/A";

    public List<GetMetadataDto> getMetadataList() {
        cancelExcessReserveTime();

        return metadataRepository.findAllByImgCountLessThan(62).stream()
                .map(GetMetadataDto::new)
                .collect(Collectors.toList());
    }

    public void cancelExcessReserveTime() {
        LocalDateTime now = LocalDateTime.now();

        List<Metadata> reservedMetadataList = metadataRepository.findAllByReserveDateNotNull();

        for (Metadata reservedMetadata : reservedMetadataList) {
            if (ChronoUnit.SECONDS.between(now, reservedMetadata.getReserveDate()) > 10800) {
                cancelReserveMetadata(reservedMetadata.getMetaSeq());
            }
        }
    }

    public void reserveMetadata(String memberId, int metaSeq) {
        int reservedCount = metadataRepository.countByReserveId(memberId);

        if (adjustmentRepository.findByMetaSeqAndAdjustId(metaSeq, memberId).isPresent())
            throw new ExistsWorkedMetadataException();

        if (reservedCount >= 3)
            throw new ExceededReservedCountLimitException();

        Metadata metadata = metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq));
        metadata.setReserveId(memberId);
        metadata.setReserveDate(LocalDateTime.now());
        metadataRepository.save(metadata);
    }

    public void cancelReserveMetadata(int metaSeq) {
        Metadata metadata = metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq));
        metadata.setReserveId(defaultReserveId);
        metadata.setReserveDate(null);
        metadataRepository.save(metadata);
    }

    public GetMetadataDto getMetadataDetail(int metaSeq) {
        return new GetMetadataDto(metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq)));
    }

    public List<GetArticleFileDto> getMetadataImages(int metaSeq, String memberId) {
        return articleFileRepository.findAllByArticleIdAndIsDeletedAndRegId(metaSeq, "0", memberId).stream()
                .map(GetArticleFileDto::new)
                .collect(Collectors.toList());
    }

    public void uploadMetadata(String memberId, MetadataRequest metadataRequest) {
        int defaultImageCount = 62;

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);

        Metadata metadata = metadataRepository.findById(metadataRequest.getMetaSeq()).orElseThrow(() -> new MetadataNotFoundException(metadataRequest.getMetaSeq()));

        for (int i = 0; i < metadataRequest.getImages().size(); i++) {
            ArticleFile articleFile = imageService.uploadMetadataImage(memberId, metadata, metadataRequest.getImages().get(i), i);
            articleFileRepository.save(articleFile);
        }

        metadata.setReserveId(defaultReserveId);
        metadata.setReserveDate(null);
        metadata.setRegId(memberId);
        metadata.setRegName(member.getMemberName());
        metadata.setRegDate(LocalDateTime.now());
        metadata.setImgCount(defaultImageCount);

        metadataRepository.save(metadata);
    }

    public void updateMetadata(String memberId, MetadataRequest metadataRequest) {

        Metadata metadata = metadataRepository.findById(metadataRequest.getMetaSeq()).orElseThrow(() -> new MetadataNotFoundException(metadataRequest.getMetaSeq()));

        int maxDisplayOrder = articleFileRepository.findTopByArticleIdOrderByArticleFileIdDesc(metadataRequest.getMetaSeq()).getDisplayOrder();

        for (int i = 0; i < metadataRequest.getImages().size(); i++) {
            ArticleFile articleFile = imageService.uploadMetadataImage(memberId, metadata, metadataRequest.getImages().get(i), maxDisplayOrder + (i + 1));
            articleFileRepository.save(articleFile);
        }
    }

    public void deleteMetadata(String memberId, DeleteMetadataRequest deleteMetadataRequest) {
        metadataRepository.findByMetaSeqAndRegId(deleteMetadataRequest.getMetaSeq(), memberId)
                .orElseThrow(RegIdIsNotWorkerException::new);

        for (int i = 0; i < deleteMetadataRequest.getArticleFileId().size(); i++) {
            ArticleFile articleFile = articleFileRepository.findById(deleteMetadataRequest.getArticleFileId().get(i))
                    .orElseThrow(ArticleFileNotFoundException::new);
            articleFile.setIsDeleted("1");
            articleFileRepository.save(articleFile);
        }
    }

    public List<GetMetadataDto> getSearchMetadata(String data) {
        return metadataRepository.findAllByArticleNameContainingOrModelNameContainingOrCompanyNameContainingAndReserveIdAndRegIdNullAndImgCountLessThan(data, data, data, defaultReserveId, 62).stream()
                .map(GetMetadataDto::new)
                .collect(Collectors.toList());
    }

    public List<GetMetadataDto> getImageSearchMetadata(List<String> data) {
        List<GetMetadataDto> getMetadataDtos = new ArrayList<>();

        for (String RegistrationNumber : data) {
            metadataRepository.findByPathImgContainingAndReserveIdAndRegIdNullAndImgCountLessThan(RegistrationNumber, defaultReserveId, 62)
                    .ifPresent(metadata -> getMetadataDtos.add(new GetMetadataDto(metadata, "photo")));

            metadataRepository.findByPathImgGoodsContainingAndReserveIdAndRegIdNullAndImgCountLessThan(RegistrationNumber, defaultReserveId, 62)
                    .ifPresent(metadata -> getMetadataDtos.add(new GetMetadataDto(metadata, "drawing")));
        }

        return getMetadataDtos;
    }
}
