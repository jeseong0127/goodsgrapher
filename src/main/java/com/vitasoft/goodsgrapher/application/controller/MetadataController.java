package com.vitasoft.goodsgrapher.application.controller;

import com.vitasoft.goodsgrapher.application.response.MetadataResponse;
import com.vitasoft.goodsgrapher.domain.service.MetadataService;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

}
