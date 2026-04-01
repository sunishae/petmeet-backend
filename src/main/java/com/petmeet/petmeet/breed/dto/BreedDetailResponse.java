package com.petmeet.petmeet.breed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BreedDetailResponse {
    private Long id;
    private String name;
    private String size;
    private int energyLevel;
    private int groomingLevel;   // 추가
    private int friendliness;    // 추가
    private int exerciseNeed;    // 추가
    private int costMin;
    private int costMax;
    private String description;  // 추가 (상세 설명)
    private String thumbnailUrl;
}
