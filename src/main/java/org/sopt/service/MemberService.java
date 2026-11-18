package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.dto.response.MemberResponse;
import org.sopt.exception.DuplicateEmailException;
import org.sopt.exception.MemberNotFoundException;
import org.sopt.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    public MemberResponse getMember(Long memberId){
        return memberRepository.findById(memberId)
                .map(MemberResponse::from)
                .orElseThrow(MemberNotFoundException::new);
    }
}
