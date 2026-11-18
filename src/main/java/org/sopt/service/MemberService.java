package org.sopt.service;

import org.sopt.dto.response.MemberResponse;
import org.sopt.exception.MemberNotFoundException;
import org.sopt.repository.MemberRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<MemberResponse> getAllMembers(){
        return memberRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(MemberResponse::from)
                .toList();
    }
}
