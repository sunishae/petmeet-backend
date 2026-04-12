package com.petmeet.petmeet.breed;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 품종(Breed) 도메인의 핵심 객체 (Entity).
// 현재는 순수 Java 객체(POJO)이며, JPA 학습 후 @Entity 애노테이션이 붙을 예정.
//
// @Getter: 모든 필드의 getter 메서드를 자동 생성 (getId(), getName() 등)
// @NoArgsConstructor(access = PROTECTED): 파라미터 없는 생성자를 protected로 생성.
//   → 외부에서 new Breed()로 빈 객체를 만드는 것을 막는다. (불완전한 객체 생성 방지)
//   → JPA를 쓸 때 내부적으로 파라미터 없는 생성자가 필요하기 때문에 미리 protected로 열어둠.
// @AllArgsConstructor: 모든 필드를 파라미터로 받는 생성자 자동 생성
//   → new Breed(1L, "골든 리트리버", ...) 형태로 객체 생성 가능
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Breed {

    private Long id;
    private String name;          // 품종 이름 (예: 골든 리트리버)
    private String size;          // 크기 (예: 대형, 소형)
    private int energyLevel;      // 활동량 (1~5)
    private int groomingLevel;    // 털 관리 난이도 (1~5)
    private int friendliness;     // 친화력 (1~5)
    private int exerciseNeed;     // 운동 필요도 (1~5)
    private int costMin;          // 월 최소 비용
    private int costMax;          // 월 최대 비용
    private String description;   // 품종 설명
    private String thumbnailUrl;  // 대표 이미지 URL
}
