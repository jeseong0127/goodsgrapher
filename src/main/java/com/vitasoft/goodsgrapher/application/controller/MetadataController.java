package com.vitasoft.goodsgrapher.application.controller;

import com.vitasoft.goodsgrapher.application.response.MetadataDetailResponse;
import com.vitasoft.goodsgrapher.application.response.MetadataResponse;
import com.vitasoft.goodsgrapher.core.security.AuthenticatedMember;
import com.vitasoft.goodsgrapher.core.security.MemberInfo;
import com.vitasoft.goodsgrapher.domain.service.MetadataService;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
public class MetadataController {

    private final MetadataService metadataService;

    @ApiOperation("메타데이터 리스트 가져오기")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public MetadataResponse getMetadataList(
    ) {
        return new MetadataResponse(metadataService.getMetadataList());
    }

    @ApiOperation("메타데이터 예약하기")
    @PutMapping("/{metaSeq}/reserve")
    @ResponseStatus(HttpStatus.OK)
    public void reserveMetadata(
            @MemberInfo AuthenticatedMember member,
            @PathVariable int metaSeq
    ) {
        metadataService.reserveMetadata(member.getMemberId(), metaSeq);
    }

    @ApiOperation("메타데이터 예약 취소하기")
    @PutMapping("/{metaSeq}/cancel-reserve")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReserveMetadata(
            @PathVariable int metaSeq
    ) {
        metadataService.cancelReserveMetadata(metaSeq);
    }

    @ApiOperation("메타데이터 작업 보기")
    @GetMapping("/{metaSeq}")
    @ResponseStatus(HttpStatus.OK)
    public MetadataDetailResponse getMetadataDetail(
            @PathVariable int metaSeq
    ) {
        return new MetadataDetailResponse(metadataService.getMetadataDetail(metaSeq));
    }
}
