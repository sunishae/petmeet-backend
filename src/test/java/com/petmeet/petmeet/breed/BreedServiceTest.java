package com.petmeet.petmeet.breed;

import com.petmeet.petmeet.breed.dto.BreedDetailResponse;
import com.petmeet.petmeet.breed.dto.BreedResponse;
import com.petmeet.petmeet.breed.exception.BreedNotFoundException;
import com.petmeet.petmeet.global.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BreedServiceTest {

    @Mock
    private BreedRepository breedRepository;

    @InjectMocks
    private BreedService breedService;

    private Breed breed1;
    private Breed breed2;

    @BeforeEach
    void setUp() {
        breed1 = new Breed(1L, "골든 리트리버", "대형", 4, 3, 5, 4, 150000, 300000, "온순하고 활발한 대형견", "https://...");
        breed2 = new Breed(2L, "비숑 프리제", "소형", 2, 4, 4, 2, 100000, 200000, "털 알레르기가 적은 소형견", "https://...");
    }

    @Test
    @DisplayName("품종 전체 목록을 조회하면 BreedResponse 리스트를 반환한다")
    void getBreeds_success() {
        // given — Repository가 이걸 반환한다고 가정
        given(breedRepository.findAll()).willReturn(List.of(breed1, breed2));

        // when — 실제 Service 메서드 호출
        List<BreedResponse> result = breedService.getBreeds();

        // then — 결과 검증
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("골든 리트리버");
        assertThat(result.get(1).getName()).isEqualTo("비숑 프리제");
    }

    @Test
    @DisplayName("존재하는 ID로 조회하면 BreedDetailResponse를 반환한다")
    void getBreed_success() {
        // given
        given(breedRepository.findById(1L)).willReturn(Optional.of(breed1));

        // when
        BreedDetailResponse result = breedService.getBreed(1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("골든 리트리버");
        assertThat(result.getEnergyLevel()).isEqualTo(4);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회하면 BreedNotFoundException이 발생한다")
    void getBreed_notFound() {
        // given
        given(breedRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> breedService.getBreed(999L))
                .isInstanceOf(BusinessException.class)
                .isInstanceOf(BreedNotFoundException.class);
    }
}
