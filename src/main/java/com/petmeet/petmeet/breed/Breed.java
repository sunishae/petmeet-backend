package com.petmeet.petmeet.breed;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Breed {

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