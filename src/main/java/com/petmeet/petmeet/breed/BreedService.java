package com.petmeet.petmeet.breed;

import com.petmeet.petmeet.breed.dto.BreedDetailResponse;
import com.petmeet.petmeet.breed.dto.BreedResponse;
import com.petmeet.petmeet.breed.exception.BreedNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BreedService {
    private BreedRepository breedRepository;

    public List<BreedResponse> getBreeds() {
        return breedRepository.findAll().stream()
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

    public BreedDetailResponse getBreed(Long id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(BreedNotFoundException::new);

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
