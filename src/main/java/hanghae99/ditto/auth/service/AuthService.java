package hanghae99.ditto.auth.service;

import hanghae99.ditto.auth.domain.MemberAuthenticationCodeEntity;
import hanghae99.ditto.auth.domain.MemberAuthenticationCodeRepository;
import hanghae99.ditto.auth.dto.request.AuthenticateCodeRequest;
import hanghae99.ditto.auth.dto.request.LoginRequest;
import hanghae99.ditto.auth.dto.request.LogoutRequest;
import hanghae99.ditto.auth.dto.request.SendEmailAuthenticationRequest;
import hanghae99.ditto.auth.dto.response.EmailAuthenticationResponse;
import hanghae99.ditto.auth.dto.response.LoginResponse;
import hanghae99.ditto.auth.support.jwt.JwtTokenProvider;
import hanghae99.ditto.auth.support.redis.RedisUtil;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberAuthenticationCodeRepository memberAuthenticationCodeRepository;
    private final MemberRepository memberRepository;
    private final EmailService emailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Transactional
    public HttpEntity<?> sendEmailAuthentication(SendEmailAuthenticationRequest sendEmailAuthenticationRequest){
        // 랜덤 인증 코드 생성
        String authenticationCode = createAuthenticationCode();

        // emailSerivce의 인증코드 보내는 메서드의 성공 여부에 따라 응답
        if (!emailService.sendEmailAuthentication(sendEmailAuthenticationRequest, authenticationCode)){
            return new ResponseEntity<>(
                    new EmailAuthenticationResponse(-1, "인증 번호 발송 실패"), HttpStatus.BAD_REQUEST
            );
        }

        // 메일 발송 성공 시
        // 아직 유효한 인증 코드 데이터를 찾아서
        Optional<MemberAuthenticationCodeEntity> beforeMemberAuthenticationCodeEntityOptional = memberAuthenticationCodeRepository
                .findByEmailAndEndDateAfterAndStatusEquals(
                        sendEmailAuthenticationRequest.getEmail(),
                        LocalDateTime.now(),
                        UsageStatus.ACTIVE);

        // 있으면 무효화 (delete_date 설정)
        if (beforeMemberAuthenticationCodeEntityOptional.isPresent()){
            MemberAuthenticationCodeEntity beforeMemberAuthenticationCodeEntity = beforeMemberAuthenticationCodeEntityOptional.get();
            beforeMemberAuthenticationCodeEntity.deleteCode();
        }

        // 인증 코드 데이터를 저장하기 위해 새 엔티티를 작성하여
        MemberAuthenticationCodeEntity memberAuthenticationCodeEntity = new MemberAuthenticationCodeEntity(sendEmailAuthenticationRequest.getEmail(), authenticationCode);

        memberAuthenticationCodeRepository.save(memberAuthenticationCodeEntity);

        return new ResponseEntity<>(
                new EmailAuthenticationResponse(0, "인증 번호 발송 성공"), HttpStatus.OK
        );

    }

    public String createAuthenticationCode(){
        return RandomStringUtils.random(8, true, true);
    }

    @Transactional
    public HttpEntity<?> authenticateCode(AuthenticateCodeRequest authenticateCodeRequest){
        // 유효한 인증 코드 데이터를 찾아서
        Optional<MemberAuthenticationCodeEntity> memberAuthenticationCodeEntityOptional =
                memberAuthenticationCodeRepository.findByEmailAndEndDateAfterAndStatusEquals(authenticateCodeRequest.getEmail(), LocalDateTime.now(), UsageStatus.ACTIVE);

        // 없으면 인증 코드 없음 반환
        if (memberAuthenticationCodeEntityOptional.isEmpty()){
            return new ResponseEntity<>(
                    new EmailAuthenticationResponse(-1, "인증 코드 없음"), HttpStatus.BAD_REQUEST
            );
        }

        // 있으면 찾아서
        MemberAuthenticationCodeEntity memberAuthenticationCodeEntity = memberAuthenticationCodeEntityOptional.get();

        // 입력한 인증 코드가 일치하는 지 검증
        if (memberAuthenticationCodeEntity.getCode().equals(authenticateCodeRequest.getCode())){

            memberAuthenticationCodeEntity.deleteCode();

            // 해당 멤버 status 인증됨으로 변경
            Member member = memberRepository.findByEmail(authenticateCodeRequest.getEmail()).orElseThrow(() -> {
                throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
            });
            member.verifiedWithEmail();

            return new ResponseEntity<>(
                    new EmailAuthenticationResponse(0, "인증 성공"), HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new EmailAuthenticationResponse(-1, "인증 실패"), HttpStatus.BAD_REQUEST
            );
        }
    }

    @Transactional
    public HttpEntity<?> login(LoginRequest loginRequest){
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> {
           throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
        });

        if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())){
            return new ResponseEntity<>(
                    new LoginResponse(null, "로그인 실패"), HttpStatus.BAD_REQUEST
            );
        } else{
            String accessToken = jwtTokenProvider.createToken(member.getId());
            member.updateLastLogin();
            return new ResponseEntity<>(
                    new LoginResponse(member.getId(), accessToken), HttpStatus.OK
            );
        }
    }

    public HttpEntity<?> logout(LogoutRequest logoutRequest){
        redisUtil.setBlackList(logoutRequest.getToken(), "accessToken", 5);
        return new ResponseEntity<>(
                new LoginResponse(1L, "로그아웃 완료"), HttpStatus.OK
        );
    }

}
