package org.sopt.controller;

import org.sopt.code.MemberSuccessCode;
import org.sopt.controller.docs.AdminControllerDocs;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.response.MemberResponse;
import org.sopt.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// /api/v1/admin/** 는 SecurityConfig에서 ROLE_ADMIN만 접근 가능
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController implements AdminControllerDocs {
    private final MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    @GetMapping("/members")
    public ResponseEntity<BaseResponse<List<MemberResponse>>> getAllMembers() {
        return ResponseEntity
                .status(MemberSuccessCode.MEMBER_LIST_READ.getHttpStatus())
                .body(BaseResponse.success(MemberSuccessCode.MEMBER_LIST_READ, memberService.getAllMembers()));
    }
}
