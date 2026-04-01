package com.petmeet.petmeet.breed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BreedResponse {
    private Long id;
    private String name;
    private String size;
    private int energyLevel;
    private int costMin;
    private int costMax;
    private String thumbnailUrl;
}
