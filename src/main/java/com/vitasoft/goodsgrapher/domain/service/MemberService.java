package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.model.dto.GetMetadataDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.MetadataRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MetadataRepository metadataRepository;

    public List<GetMetadataDto> getMetadata(String memberId) {
        return metadataRepository.findAllByReserveIdOrRegId(memberId, memberId).stream()
                .map(GetMetadataDto::new)
                .collect(Collectors.toList());
    }
}
