# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**petmeet-backend** — 예비 반려인 입양 전 지원 플랫폼 백엔드. Spring Boot 3.5 기반 REST API 서버.

- Java 21, Spring Boot 3.5.x, Gradle
- 현재 인메모리 저장소 사용 (JPA 학습 후 H2 → MySQL로 교체 예정)
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- H2 Console: `http://localhost:8080/h2-console`

## Commands

```bash
# 실행
./gradlew bootRun

# 빌드
./gradlew build

# 전체 테스트
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "com.petmeet.petmeet.breed.BreedServiceTest"

# 특정 테스트 메서드 실행
./gradlew test --tests "com.petmeet.petmeet.breed.BreedServiceTest.getBreed_success"
```

## Architecture

### 패키지 구조

```
com.petmeet.petmeet
├── PetmeetApplication.java
├── global
│   ├── config/          # WebMvcConfiguration, SwaggerConfig
│   ├── exception/       # ErrorCode, BusinessException, GlobalExceptionHandler
│   └── response/        # ErrorResponse
├── member               # 회원 도메인
├── breed                # 품종 도메인
├── survey               # 설문 & 매칭 도메인
├── post                 # 커뮤니티 게시글 도메인
├── comment              # 댓글 도메인
└── cost                 # 비용 정보 도메인
```

각 도메인 내부 구조:
```
XxxController.java           # URL 매핑, 요청/응답 변환
XxxService.java              # 비즈니스 로직
XxxRepository.java           # 데이터 접근 (현재 ArrayList 인메모리)
Xxx.java                     # Entity (Plain POJO, JPA 없음)
dto/XxxRequest.java          # 요청 DTO
dto/XxxResponse.java         # 응답 DTO
exception/XxxException.java  # 도메인 전용 예외
```

### 의존 방향 원칙

- **단방향 엄수**: `Controller → Service → Repository`
- **도메인 간 참조**: 다른 도메인의 Repository 직접 참조 금지 — 반드시 해당 도메인의 Service를 통해 접근

### 에러 처리 패턴

1. `ErrorCode` enum에 새 코드 추가 (도메인별 주석 그룹으로 분류)
2. `BusinessException`을 상속한 도메인 전용 예외 클래스 생성 → `super(ErrorCode.XXX)` 호출
3. Service에서 도메인 예외를 throw → `GlobalExceptionHandler`가 자동으로 처리

`RuntimeException` 직접 사용 금지. 모든 예외는 `BusinessException` + `ErrorCode` 패턴으로 처리.

### API URL 컨벤션

- Prefix: `/api/v1/`
- 명사 복수형: `/api/v1/breeds`, `/api/v1/members`
- Path variable: `/api/v1/breeds/{id}`

### Validation

Request DTO에 Jakarta Bean Validation 애노테이션 사용 (`@NotBlank`, `@Email`, `@Size` 등).  
`GlobalExceptionHandler`가 모든 필드 에러를 쉼표로 조합하여 단일 메시지로 반환.

## 코드 리뷰 기준

코드 리뷰 시 아래 항목을 기준으로 피드백:

1. **계층 분리**: Controller에 비즈니스 로직이 있으면 Service로 분리
2. **예외 처리**: `BusinessException` + `ErrorCode` 패턴 사용, `RuntimeException` 직접 사용 금지
3. **DTO 분리**: Entity를 Controller에서 직접 반환하거나 받지 않음
4. **의존 방향**: 역방향 의존, 도메인 간 Repository 직접 참조 없는지 확인
5. **Lombok**: `@Setter` 남용 금지, `@Data` 사용 지양
6. **도메인 간 참조**: Repository 직접 참조 대신 Service를 통해 접근하는지 확인
7. **Validation**: Request DTO에 `@Valid` 관련 애노테이션 적절히 사용하는지 확인

## 커밋 & 브랜치 컨벤션

커밋 메시지 형식: `type(도메인): 한글 설명`  
타입: `feat`, `fix`, `refactor`, `chore`, `docs`, `test`

브랜치 전략:
- `main` — 배포 브랜치
- `develop` — 개발 통합 브랜치
- `feature/*` — 기능 개발
- `fix/*` — 버그 수정

PR 템플릿: `.github/pull_request_template.md` 사용. 관련 이슈는 `Closes #이슈번호`로 닫기.

## 구현 현황

**완료**: `global` (예외/응답 인프라), `breed` (전체 도메인)  
**진행 중**: `member` (엔티티, Repository 구현 중)  
**미구현**: `survey`, `post`, `comment`, `cost`, Auth(JWT), JPA 전환

## 참고 문서

- API 명세: https://www.notion.so/3358978d5b01811281efc58fe4e99e0d
- ERD 설계: https://www.notion.so/3358978d5b0181a1962ce684b172087c
- 패키지 구조: https://www.notion.so/3358978d5b0181c390c0c11ce58029d5
- GitHub 협업 전략: https://www.notion.so/3358978d5b0181188187e55a2f947441
