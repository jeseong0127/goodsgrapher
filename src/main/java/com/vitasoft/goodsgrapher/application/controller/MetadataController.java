package com.vitasoft.goodsgrapher.application.controller;

import com.vitasoft.goodsgrapher.application.request.DeleteMetadataRequest;
import com.vitasoft.goodsgrapher.application.request.MetadataRequest;
import com.vitasoft.goodsgrapher.application.response.ArticleFileResponse;
import com.vitasoft.goodsgrapher.application.response.MetadataDetailResponse;
import com.vitasoft.goodsgrapher.application.response.MetadataResponse;
import com.vitasoft.goodsgrapher.core.security.AuthenticatedMember;
import com.vitasoft.goodsgrapher.core.security.MemberInfo;
import com.vitasoft.goodsgrapher.domain.service.MetadataService;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
public class MetadataController {
    private final MetadataService metadataService;

    @ApiOperation("메타데이터 조회하기")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MetadataResponse getMetadata(
            @RequestParam(required = false) String word,
            @RequestParam(required = false) List<String> images
    ) {
        if (word != null) {
            return new MetadataResponse(metadataService.getSearchMetadata(word));
        } else if (images != null) {
            return new MetadataResponse(metadataService.getImageSearchMetadata(images));
        } else {
            return new MetadataResponse(metadataService.getMetadataList());
        }
    }

    @ApiOperation("메타데이터 예약하기")
    @PostMapping("/reservation/{metaSeq}")
    @ResponseStatus(HttpStatus.OK)
    public void reserveMetadata(
            @MemberInfo AuthenticatedMember member,
            @PathVariable int metaSeq
    ) {
        metadataService.reserveMetadata(member.getMemberId(), metaSeq);
    }

    @ApiOperation("메타데이터 예약 취소하기")
    @DeleteMapping("/reservation/{metaSeq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReserveMetadata(
            @PathVariable int metaSeq
    ) {
        metadataService.cancelReserveMetadata(metaSeq);
    }

    @ApiOperation("메타데이터 작업보기")
    @GetMapping("/{metaSeq}")
    @ResponseStatus(HttpStatus.OK)
    public MetadataDetailResponse getMetadataDetail(
            @PathVariable int metaSeq
    ) {
        return new MetadataDetailResponse(metadataService.getMetadataDetail(metaSeq));
    }

    @ApiOperation("메타데이터 작업한 이미지보기")
    @GetMapping("/{metaSeq}/images")
    @ResponseStatus(HttpStatus.OK)
    public ArticleFileResponse getMetadataImages(
            @PathVariable int metaSeq
    ) {
        return new ArticleFileResponse(metadataService.getMetadataImages(metaSeq));
    }

    @ApiOperation("메타데이터 작업하기")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMetadata(
            @MemberInfo AuthenticatedMember member,
            @Valid @ModelAttribute MetadataRequest metadataRequest
    ) {
        metadataService.uploadMetadata(member.getMemberId(), metadataRequest);
    }

    @ApiOperation("메타데이터 수정하기")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMetadata(
            @MemberInfo AuthenticatedMember member,
            @Valid @ModelAttribute MetadataRequest metadataRequest
    ) {
        metadataService.updateMetadata(member.getMemberId(), metadataRequest);
    }

    @ApiOperation("메타데이터 삭제하기")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMetadata(
            @MemberInfo AuthenticatedMember member,
            @Valid @ModelAttribute DeleteMetadataRequest deleteMetadataRequest
    ) {
        metadataService.deleteMetadata(member.getMemberId(), deleteMetadataRequest);
    }
}
