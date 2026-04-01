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
    private int groomingLevel;
    private int friendliness;
    private int exerciseNeed;
    private int costMin;
    private int costMax;
    private String description;
    private String thumbnailUrl;
}
