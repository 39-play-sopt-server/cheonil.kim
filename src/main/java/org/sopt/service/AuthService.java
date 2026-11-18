package org.sopt.service;

import org.sopt.code.AuthErrorCode;
import org.sopt.domain.Member;
import org.sopt.domain.RefreshToken;
import org.sopt.dto.request.LoginRequest;
import org.sopt.dto.request.SignupRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.exception.AuthException;
import org.sopt.exception.DuplicateEmailException;
import org.sopt.exception.MemberNotFoundException;
import org.sopt.jwt.*;
import org.sopt.repository.MemberRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.RefreshTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenIssuer tokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PostRepository postRepository;
    private final TokenParser tokenParser;
    private final TokenBlacklist tokenBlacklist;

    public AuthService(MemberRepository memberRepository,
                       PasswordEncoder passwordEncoder,
                       TokenIssuer tokenIssuer,
                       RefreshTokenRepository refreshTokenRepository,
                       PostRepository postRepository,
                       TokenParser tokenParser,
                       TokenBlacklist tokenBlacklist) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.refreshTokenRepository = refreshTokenRepository;
        this.postRepository = postRepository;
        this.tokenParser = tokenParser;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Transactional
    public MemberResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }

        Member member = Member.create(request.email(), request.name(), passwordEncoder.encode(request.password()));
        return MemberResponse.from(memberRepository.save(member));
    }

    @Transactional
    public AuthTokens login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .filter(m -> passwordEncoder.matches(request.password(), m.getPassword()))
                .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));
        return issueTokens(member);
    }

    @Transactional(noRollbackFor = AuthException.class)
    public AuthTokens reissue(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        TokenClaims claims = tokenParser.parse(refreshToken);
        if (claims.type() != TokenType.REFRESH) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN_TYPE);
        }

        RefreshToken saved = refreshTokenRepository.findByMemberId(claims.memberId())
                .orElseThrow(() -> new AuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!saved.matches(claims.tokenId())) {
            refreshTokenRepository.delete(saved);
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_REUSED);
        }

        Member member = memberRepository.findById(claims.memberId())
                .orElseThrow(MemberNotFoundException::new);
        return issueTokens(member);
    }

    @Transactional
    public void logout(Long memberId, TokenClaims accessToken) {
        refreshTokenRepository.deleteByMemberId(memberId);
        tokenBlacklist.add(accessToken.tokenId(), accessToken.expiresAt());
    }

    @Transactional
    public void withdraw(Long memberId, TokenClaims accessToken) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
        postRepository.decreaseLikeCountByLiker(memberId);   // 내가 누른 좋아요 수 보정
        postRepository.deleteAllByMemberId(memberId);        // 내 게시글 삭제, 게시글의 좋아요는 DB cascade
        refreshTokenRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);               // 내 좋아요 행은 DB cascade
        tokenBlacklist.add(accessToken.tokenId(), accessToken.expiresAt());
    }

    private AuthTokens issueTokens(Member member) {
        IssuedToken accessToken = tokenIssuer.issue(member.getId(), member.getRole(), TokenType.ACCESS);
        IssuedToken refreshToken = tokenIssuer.issue(member.getId(), member.getRole(), TokenType.REFRESH);

        refreshTokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        saved -> saved.rotate(refreshToken.tokenId(), refreshToken.expiresAt()),
                        () -> refreshTokenRepository.save(
                                RefreshToken.create(member.getId(), refreshToken.tokenId(), refreshToken.expiresAt()))
                );

        return new AuthTokens(accessToken.value(), refreshToken);
    }
}