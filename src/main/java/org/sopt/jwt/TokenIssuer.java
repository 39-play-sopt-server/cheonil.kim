package org.sopt.jwt;

import org.sopt.domain.Role;

public interface TokenIssuer {
    IssuedToken issue(Long memberId, Role role, TokenType type);
}
