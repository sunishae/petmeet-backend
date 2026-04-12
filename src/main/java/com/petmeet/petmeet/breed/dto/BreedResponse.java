package com.petmeet.petmeet.breed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 품종 목록 조회 시 반환하는 응답 DTO.
// 목록에서는 핵심 정보만 보여주고 상세 정보(설명, 친화력 등)는 제외.
// → 불필요한 데이터 전송을 줄이고, 목록 화면에 필요한 데이터만 내려준다.
//
// DTO(Data Transfer Object): 계층 간 데이터를 전달하기 위한 전용 객체.
//   Entity(Breed)를 직접 반환하면 DB 구조가 API에 그대로 노출되고,
//   나중에 DB 구조가 바뀌면 API 스펙도 함께 바뀌어버리는 문제가 생긴다.
//   DTO를 쓰면 DB 구조와 API 스펙을 독립적으로 관리할 수 있다.
@Getter
@AllArgsConstructor
public class BreedResponse {
    private Long id;
    private String name;         // 품종 이름
    private String size;         // 크기
    private int energyLevel;     // 활동량
    private int costMin;         // 월 최소 비용
    private int costMax;         // 월 최대 비용
    private String thumbnailUrl; // 대표 이미지 URL
}
