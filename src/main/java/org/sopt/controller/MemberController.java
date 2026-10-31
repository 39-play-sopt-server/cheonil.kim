package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.code.MemberSuccessCode;
import org.sopt.controller.docs.MemberControllerDocs;
import org.sopt.dto.BaseResponse;
import org.sopt.dto.request.MemberCreateRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/members")
public class MemberController implements MemberControllerDocs {
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<MemberResponse>> createMember(
            @Valid @RequestBody MemberCreateRequest request
    ){
        return ResponseEntity
                .status(MemberSuccessCode.MEMBER_CREATED.getHttpStatus())
                .body(BaseResponse.success(MemberSuccessCode.MEMBER_CREATED,
                        memberService.createMember(request)));
    }

    @Override
    @DeleteMapping(path = "/{memberId}")
    public ResponseEntity<BaseResponse<Void>> deleteMember(
            @PathVariable(name = "memberId") Long id
    ){
        memberService.deleteMember(id);
        return ResponseEntity
                .status(MemberSuccessCode.MEMBER_DELETED.getHttpStatus())
                .body(BaseResponse.success(MemberSuccessCode.MEMBER_DELETED));
    }
}
