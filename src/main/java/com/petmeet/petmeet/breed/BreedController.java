package com.petmeet.petmeet.breed;

import com.petmeet.petmeet.breed.dto.BreedDetailResponse;
import com.petmeet.petmeet.breed.dto.BreedResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 품종 관련 HTTP 요청을 받아서 응답을 반환하는 컨트롤러.
// 비즈니스 로직은 직접 처리하지 않고, 반드시 Service에 위임한다.
//
// @RestController: @Controller + @ResponseBody 합친 것.
//   → 메서드의 반환값을 JSON으로 직렬화해서 HTTP 응답 바디에 담아 반환.
// @RequestMapping("/api/v1/breeds"): 이 컨트롤러의 모든 메서드 URL 앞에 /api/v1/breeds가 붙음.
@RestController
@RequestMapping("/api/v1/breeds")
@RequiredArgsConstructor
public class BreedController {

    private final BreedService breedService;

    // GET /api/v1/breeds → 전체 품종 목록 반환
    // ResponseEntity: HTTP 상태코드 + 응답 바디를 함께 제어할 수 있는 래퍼 클래스.
    // ResponseEntity.ok() → HTTP 200 OK 상태코드와 함께 데이터를 반환.
    @GetMapping
    public ResponseEntity<List<BreedResponse>> getBreeds() {
        return ResponseEntity.ok(breedService.getBreeds());
    }

    // GET /api/v1/breeds/{id} → 특정 품종 상세 반환
    // @PathVariable: URL 경로의 {id} 값을 메서드 파라미터로 받아온다.
    //   → 예: GET /api/v1/breeds/3 요청 시 id = 3
    @GetMapping("/{id}")
    public ResponseEntity<BreedDetailResponse> getBreed(@PathVariable Long id) {
        return ResponseEntity.ok(breedService.getBreed(id));
    }
}
