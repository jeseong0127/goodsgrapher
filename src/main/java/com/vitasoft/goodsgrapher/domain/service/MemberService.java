package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.model.dto.GetAccountsDto;
import com.vitasoft.goodsgrapher.domain.model.dto.GetMetadataDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.AdjustmentRepository;
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

    public List<GetMetadataDto> getMetadata(String memberId) {
        return metadataRepository.findAllByReserveIdOrRegId(memberId, memberId).stream()
                .map(GetMetadataDto::new)
                .collect(Collectors.toList());
    }

    public List<GetAccountsDto> getAccounts(String memberId) {
        return adjustmentRepository.findByRegId(memberId).stream()
                .map(GetAccountsDto::new)
                .collect(Collectors.toList());
    }
}
