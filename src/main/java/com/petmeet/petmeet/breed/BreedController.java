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

@RestController
@RequestMapping("/api/v1/breeds")
@RequiredArgsConstructor
public class BreedController {

    private final BreedService breedService;

    @GetMapping
    public ResponseEntity<List<BreedResponse>> getBreeds() {
        return ResponseEntity.ok(breedService.getBreeds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedDetailResponse> getBreed(@PathVariable Long id) {
        return ResponseEntity.ok(breedService.getBreed(id));
    }
}
