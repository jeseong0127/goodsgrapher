package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.application.response.AccountDetailResponse;
import com.vitasoft.goodsgrapher.domain.exception.metadata.MetadataNotFoundException;
import com.vitasoft.goodsgrapher.domain.model.dto.GetAccountsDto;
import com.vitasoft.goodsgrapher.domain.model.dto.GetMetadataDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.AdjustmentRepository;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.ArticleFileRepository;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.MetadataRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MetadataRepository metadataRepository;

    private final AdjustmentRepository adjustmentRepository;

    private final ArticleFileRepository articleFileRepository;

    public List<GetMetadataDto> getMetadata(String memberId) {
        return metadataRepository.findAllByReserveIdOrRegId(memberId, memberId).stream()
                .map(metadata -> new GetMetadataDto(metadata, articleFileRepository.countByArticleIdAndRegIdAndIsDeleted(metadata.getMetaSeq(), metadata.getRegId(), "0")))
                .collect(Collectors.toList());
    }

    public List<GetAccountsDto> getAccounts(String memberId) {
        return adjustmentRepository.findByRegId(memberId).stream()
                .map(GetAccountsDto::new)
                .collect(Collectors.toList());
    }

    public AccountDetailResponse getAccountDetail(int metaSeq, String memberId) {
        Metadata metadata = metadataRepository.findById(metaSeq).orElseThrow(() -> new MetadataNotFoundException(metaSeq));

        List<ArticleFile> articleFile = articleFileRepository.findAllByBoardNameAndArticleIdAndIsDeletedAndRegId("METAIMG", metaSeq, "0", memberId);

        return new AccountDetailResponse(metadata, articleFile);
    }
}
