package com.zerock.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 추후 페이징 DTO 상속을 고려한 기본 페이지 요청 DTO.
 * 검색 조건에 따라 page, size 값이 변경될 수 있음을 대비함.
 */
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;
}
