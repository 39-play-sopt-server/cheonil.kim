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
    private final String dummyPasswordHash;

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
        this.dummyPasswordHash = passwordEncoder.encode("dummy-password-for-timing");
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
        Member member = memberRepository.findByEmail(request.email()).orElse(null);

        // 없는 이메일이어도 BCrypt 비교를 한 번 수행해 응답 시간으로 가입 여부가 드러나지 않게 함
        String encodedPassword = (member != null) ? member.getPassword() : dummyPasswordHash;
        boolean matches = passwordEncoder.matches(request.password(), encodedPassword);
        if (member == null || !matches) {
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        AuthTokens tokens = issueTokens(member);
        refreshTokenRepository.upsert(member.getId(), tokens.refreshToken().tokenId(), tokens.refreshToken().expiresAt());
        return tokens;
    }

    // 재사용 감지 시 화이트리스트 삭제가 롤백되면 안 되므로 AuthException은 롤백 제외
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
            throw revokeAsReused(claims.memberId());
        }

        // role이 바뀌었을 수 있으니 토큰이 아니라 DB에서 다시 읽음
        Member member = memberRepository.findById(claims.memberId())
                .orElseThrow(MemberNotFoundException::new);
        AuthTokens tokens = issueTokens(member);

        int rotated = refreshTokenRepository.rotateIfMatches(
                member.getId(), claims.tokenId(), tokens.refreshToken().tokenId(), tokens.refreshToken().expiresAt());
        if (rotated == 0) {
            // 같은 Refresh로 들어온 다른 요청이 먼저 교체함 → 한 토큰이 두 번 쓰인 것이므로 재사용으로 처리
            throw revokeAsReused(member.getId());
        }
        return tokens;
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
        return new AuthTokens(accessToken.value(), refreshToken);
    }

    // 이미 교체된 Refresh가 다시 들어옴 → 탈취 가능성, 해당 회원의 Refresh 무효화 후 재로그인 요구
    private AuthException revokeAsReused(Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
        return new AuthException(AuthErrorCode.REFRESH_TOKEN_REUSED);
    }
}
