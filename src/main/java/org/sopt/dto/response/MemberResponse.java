package org.sopt.dto.response;

import org.sopt.domain.Member;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String email,
        String name,
        LocalDateTime createdAt
) {
    public static MemberResponse from(Member member){
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getCreatedAt()
        );
    }
}
