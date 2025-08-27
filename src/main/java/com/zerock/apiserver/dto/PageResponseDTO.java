package com.zerock.apiserver.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 제네릭을 사용하는 이유
 * - 다양한 엔티티 타입에 대해 재사용 가능한 페이징 DTO를 만들기 위함
 * - 한 페이지에 여러 엔티티 타입의 페이징 처리를 하나의 DTO로 관리 가능
 *
 * @param <E> 페이징 처리 대상인 엔티티 또는 DTO 타입
 */

@Data
public class PageResponseDTO<E> {
    // 페이징 처리된 데이터 리스트
    private List<E> dtoList;

    // 화면에 보여줄 페이지 번호 목록 (예: 1, 2, 3, ..., 10)
    private List<Integer> pageNumList;

    // 요청한 페이징 정보 (페이지 번호, 페이지 크기 등)
    private PageRequestDTO pageRequestDTO;

    // 이전 페이지 버튼 활성화 여부 (현재 페이지가 1 이상일 때 true)
    private boolean prev;

    // 다음 페이지 버튼 활성화 여부 (마지막 페이지가 아닐 때 true)
    private boolean next;

    // 전체 데이터 개수
    private int totalCount;

    // 이전 페이지 번호 (prev가 true일 때 의미 있음)
    private int prevPage;

    // 다음 페이지 번호 (next가 true일 때 의미 있음)
    private int nextPage;

    // 전체 페이지 수
    private int totalPage;

    // 현재 페이지 번호
    private int current;

    private PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) total; // long 타입을 int로 변환 (필요 시 다운캐스팅)

        // 1~10, 11~20 등 10개 단위 페이지 그룹 계산
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10; // 그룹의 마지막 페이지 번호
        int start = end - 9; // 그룹의 시작 페이지 번호

        // 전체 마지막 페이지 번호 계산
        int last = (int) Math.ceil(totalCount / (double) pageRequestDTO.getSize());

        // 그룹 마지막 페이지가 전체 마지막 페이지를 넘지 않도록 조정
        end = Math.min(end, last);

        // 이전 버튼 활성화 여부 (시작 페이지가 1보다 클 때 활성화)
        this.prev = start > 1;

        // 다음 버튼 활성화 여부 (전체 개수가 현재 그룹의 마지막 페이지 * 페이지 크기보다 클 때 활성화)
        this.next = totalCount > end * pageRequestDTO.getSize();

        // 화면에 보여줄 페이지 번호 리스트 생성
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        // 이전 페이지 번호 세팅 (이전 버튼이 있을 때 시작 페이지 - 1)
        this.prevPage = prev ? start - 1 : 0;

        // 다음 페이지 번호 세팅 (다음 버튼이 있을 때 마지막 페이지 + 1)
        this.nextPage = next ? end + 1 : 0;

        // 전체 페이지, 현재 페이지 값 세팅
        this.totalPage = last;
        this.current = pageRequestDTO.getPage();
    }
}

