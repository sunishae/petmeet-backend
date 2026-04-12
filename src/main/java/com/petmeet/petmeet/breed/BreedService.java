package com.petmeet.petmeet.breed;

import com.petmeet.petmeet.breed.dto.BreedDetailResponse;
import com.petmeet.petmeet.breed.dto.BreedResponse;
import com.petmeet.petmeet.breed.exception.BreedNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 품종 도메인의 비즈니스 로직을 담당하는 클래스.
// Controller에서 받은 요청을 처리하고, Repository를 통해 데이터를 가져와 DTO로 변환해서 반환.
//
// @Service: 이 클래스가 비즈니스 로직 계층임을 Spring에 알리는 애노테이션.
// @RequiredArgsConstructor: final 필드를 파라미터로 받는 생성자를 자동 생성.
//   → Spring이 이 생성자를 통해 BreedRepository를 자동으로 주입해준다. (생성자 주입)
@Service
@RequiredArgsConstructor
public class BreedService {

    // final: 한번 주입되면 바뀌지 않음을 보장. @RequiredArgsConstructor가 생성자로 주입해줌.
    private final BreedRepository breedRepository;

    // 전체 품종 목록 조회.
    // Entity(Breed)를 그대로 반환하지 않고 BreedResponse DTO로 변환해서 반환한다.
    // → 클라이언트에게 필요한 데이터만 골라서 내려주기 위함.
    public List<BreedResponse> getBreeds() {
        return breedRepository.findAll().stream()
                // 각 Breed 객체를 BreedResponse로 변환 (목록용이라 상세 정보는 제외)
                .map(breed -> new BreedResponse(
                        breed.getId(),
                        breed.getName(),
                        breed.getSize(),
                        breed.getEnergyLevel(),
                        breed.getCostMin(),
                        breed.getCostMax(),
                        breed.getThumbnailUrl()
                ))
                .collect(Collectors.toList());
    }

    // 특정 품종 상세 조회.
    // orElseThrow: Optional에 값이 없으면(품종을 못 찾으면) 괄호 안의 예외를 던진다.
    // BreedNotFoundException → GlobalExceptionHandler → 404 응답으로 이어지는 흐름.
    public BreedDetailResponse getBreed(Long id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(BreedNotFoundException::new);

        // 상세 조회이므로 모든 필드를 포함한 BreedDetailResponse로 변환
        return new BreedDetailResponse(
                breed.getId(),
                breed.getName(),
                breed.getSize(),
                breed.getEnergyLevel(),
                breed.getGroomingLevel(),
                breed.getFriendliness(),
                breed.getExerciseNeed(),
                breed.getCostMin(),
                breed.getCostMax(),
                breed.getDescription(),
                breed.getThumbnailUrl()
        );
    }
}
