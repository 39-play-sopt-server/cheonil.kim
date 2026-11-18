package org.sopt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.domain.Member;
import org.sopt.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 계정은 회원가입 API로 만들 수 없도록 하고, 서버 시작 시 환경변수 값으로 한 번만 생성한다.
 * 같은 이메일이 이미 있으면 권한을 바꾸지 않고 건너뛴다.
 * (누군가 관리자 이메일로 먼저 가입해 두는 경우 그 계정이 관리자로 승격되는 것을 막기 위함)
 */
@Component
public class AdminAccountInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminAccountInitializer.class);
    private static final String ADMIN_NAME = "관리자";

    private final AdminProperties adminProperties;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminAccountInitializer(AdminProperties adminProperties,
                                   MemberRepository memberRepository,
                                   PasswordEncoder passwordEncoder) {
        this.adminProperties = adminProperties;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!adminProperties.isConfigured()) {
            log.info("관리자 계정 설정이 없어 생성을 건너뜁니다.");
            return;
        }
        if (memberRepository.existsByEmail(adminProperties.email())) {
            log.info("관리자 이메일의 계정이 이미 존재해 생성을 건너뜁니다.");
            return;
        }
        memberRepository.save(Member.createAdmin(
                adminProperties.email(),
                ADMIN_NAME,
                passwordEncoder.encode(adminProperties.password())
        ));
        log.info("관리자 계정을 생성했습니다.");
    }
}
