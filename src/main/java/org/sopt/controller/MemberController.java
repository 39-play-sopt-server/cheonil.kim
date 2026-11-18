package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.code.MemberSuccessCode;
import org.sopt.controller.docs.MemberControllerDocs;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.response.MemberResponse;
import org.sopt.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController implements MemberControllerDocs {
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<MemberResponse>> getMyInfo(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity
                .status(MemberSuccessCode.MEMBER_READ.getHttpStatus())
                .body(BaseResponse.success(MemberSuccessCode.MEMBER_READ, memberService.getMember(memberId)));
    }
}
