package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.dto.request.MemberCreateRequest;
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

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request){
        if(memberRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException();
        }
        Member member = Member.create(request.email(), request.name());
        return MemberResponse.from(memberRepository.save(member));
    }

    @Transactional
    public void deleteMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }
}
