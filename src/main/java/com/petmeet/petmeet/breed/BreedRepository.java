package com.petmeet.petmeet.breed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BreedRepository {
    private final List<Breed> breeds = new ArrayList<>();

    public List<Breed> findAll() {
        return breeds;
    }

    public Optional<Breed> findById(Long id) {
        return breeds.stream()
                .filter(breed -> breed.getId().equals(id))
                .findFirst();
    }

    public void save(Breed breed) {
        breeds.add(breed);
    }
}
