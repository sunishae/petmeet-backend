package com.petmeet.petmeet.member;

import com.petmeet.petmeet.member.dto.MemberJoinRequest;
import com.petmeet.petmeet.member.dto.MemberLoginRequest;
import com.petmeet.petmeet.member.dto.MemberResponse;
import com.petmeet.petmeet.member.exception.DuplicateEmailException;
import com.petmeet.petmeet.member.exception.InvalidCredentialsException;
import com.petmeet.petmeet.member.exception.MemberNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 회원 도메인의 비즈니스 로직을 담당하는 클래스.
// "이 이메일이 이미 있는지 확인한다", "비밀번호가 맞는지 확인한다" 같은
// 실제 서비스 규칙들이 여기에 구현된다.
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입 처리.
    // 순서: 이메일 중복 확인 → Member 객체 생성 → 저장 → 응답 DTO 반환
    public MemberResponse join(MemberJoinRequest request) {
        // 1. 이미 가입된 이메일인지 확인. 있으면 바로 예외를 던지고 메서드 종료.
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        // 2. 요청 데이터로 Member 객체 생성
        //    id는 Repository에서 자동 생성하므로 null로 전달.
        //    deletedAt은 가입 시 null (탈퇴하지 않은 상태).
        Member member = new Member(
                null,
                request.getEmail(),
                request.getPassword(), // 현재 평문 저장 → Auth 학습 후 BCrypt 해싱으로 교체 예정
                request.getNickname(),
                LocalDateTime.now(),
                null
        );

        // 3. 저장 (저장 후 ID가 채워진 새 객체를 반환받음)
        Member saved = memberRepository.save(member);
        return new MemberResponse(saved.getId(), saved.getEmail(), saved.getNickname());
    }

    // 로그인 처리.
    // 순서: 이메일로 회원 조회 → 비밀번호 일치 확인 → 응답 DTO 반환
    public MemberResponse login(MemberLoginRequest request) {
        // 1. 이메일로 회원 조회. 없으면 InvalidCredentialsException 던짐.
        //    "이메일이 없습니다"가 아닌 "이메일 또는 비밀번호가 틀립니다"로 응답하는 이유:
        //    어느 쪽이 틀렸는지 알려주면 공격자가 계정 존재 여부를 파악할 수 있기 때문.
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // 2. 비밀번호 일치 확인. 틀리면 동일한 예외를 던짐.
        if (!member.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new MemberResponse(member.getId(), member.getEmail(), member.getNickname());
    }

    // 회원 정보 조회.
    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        return new MemberResponse(member.getId(), member.getEmail(), member.getNickname());
    }
}
