package com.petmeet.petmeet.breed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 품종 상세 조회 시 반환하는 응답 DTO.
// 목록(BreedResponse)보다 더 많은 정보를 포함한다.
// 같은 Breed 엔티티에서 나오지만, 용도에 따라 DTO를 분리하는 것이 올바른 설계.
@Getter
@AllArgsConstructor
public class BreedDetailResponse {
    private Long id;
    private String name;          // 품종 이름
    private String size;          // 크기
    private int energyLevel;      // 활동량
    private int groomingLevel;    // 털 관리 난이도
    private int friendliness;     // 친화력
    private int exerciseNeed;     // 운동 필요도
    private int costMin;          // 월 최소 비용
    private int costMax;          // 월 최대 비용
    private String description;   // 품종 설명
    private String thumbnailUrl;  // 대표 이미지 URL
}
