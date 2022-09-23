package com.vitasoft.goodsgrapher.application.response;

import com.vitasoft.goodsgrapher.domain.model.dto.GetAdjustmentDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdjustmentResponse {
    private List<GetAdjustmentDto> adjustments;
}
