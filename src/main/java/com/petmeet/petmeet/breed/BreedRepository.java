package com.petmeet.petmeet.breed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

// 품종 데이터에 접근하는 저장소 클래스.
// 현재는 DB 없이 서버 메모리(ArrayList)에 데이터를 저장한다.
// JPA 학습 후 이 클래스가 JpaRepository 인터페이스로 교체될 예정.
//
// @Repository: 이 클래스가 데이터 접근 계층임을 Spring에 알리는 애노테이션.
// Spring이 자동으로 Bean으로 등록해줘서 다른 클래스에서 주입받아 쓸 수 있다.
@Repository
public class BreedRepository {

    // 서버가 실행되는 동안만 데이터가 유지되는 임시 저장소.
    // 서버를 재시작하면 데이터가 모두 사라진다.
    private final List<Breed> breeds = new ArrayList<>();

    // 모든 품종 목록 반환
    public List<Breed> findAll() {
        return breeds;
    }

    // ID로 특정 품종 조회.
    // Optional: 값이 있을 수도, 없을 수도 있음을 표현하는 컨테이너.
    // → null을 직접 다루지 않아도 되므로 NullPointerException 방지에 효과적.
    public Optional<Breed> findById(Long id) {
        return breeds.stream()
                .filter(breed -> breed.getId().equals(id))
                .findFirst();
    }

    // 품종 저장
    public void save(Breed breed) {
        breeds.add(breed);
    }
}
