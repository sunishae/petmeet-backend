package com.petmeet.petmeet.member;

import com.petmeet.petmeet.member.dto.MemberJoinRequest;
import com.petmeet.petmeet.member.dto.MemberLoginRequest;
import com.petmeet.petmeet.member.dto.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 회원 관련 HTTP 요청을 받아서 응답을 반환하는 컨트롤러.
// Controller의 역할은 딱 두 가지: 요청을 받고, 응답을 반환.
// 비즈니스 로직(이메일 중복 확인 등)은 절대 여기서 처리하지 않는다.
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // POST /api/v1/members/join → 회원가입
    // @RequestBody: HTTP 요청 바디의 JSON을 MemberJoinRequest 객체로 변환해줌
    // @Valid: MemberJoinRequest에 선언된 검증 애노테이션(@NotBlank, @Email 등)을 실행하도록 트리거
    //   → @Valid 없으면 검증 애노테이션이 있어도 검증이 실행되지 않음. 반드시 붙여야 한다.
    @PostMapping("/join")
    public ResponseEntity<MemberResponse> join(@RequestBody @Valid MemberJoinRequest request) {
        return ResponseEntity.ok(memberService.join(request));
    }

    // POST /api/v1/members/login → 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody @Valid MemberLoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    // GET /api/v1/members/{id} → 회원 정보 조회
    // @PathVariable: URL 경로의 {id} 값을 Long id 파라미터로 받아온다.
    //   → 예: GET /api/v1/members/5 요청 시 id = 5
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }
}
