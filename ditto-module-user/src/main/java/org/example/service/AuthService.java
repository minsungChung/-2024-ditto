package org.example.service;

import org.example.domain.MemberAuthenticationCodeEntity;
import org.example.domain.MemberAuthenticationCodeRepository;
import org.example.dto.request.AuthenticateCodeRequest;
import org.example.dto.request.LoginRequest;
import org.example.dto.request.LogoutRequest;
import org.example.dto.request.SendEmailAuthenticationRequest;
import org.example.dto.response.EmailAuthenticationResponse;
import org.example.dto.response.LoginResponse;
import org.example.global.exception.InvalidAccessException;
import org.example.global.exception.InvalidEmailException;
import org.example.global.support.jwt.JwtTokenProvider;
//import org.example.global.support.redis.RedisUtil;
import org.example.domain.Member;
import org.example.domain.MemberRepository;
import org.example.global.exception.NoSuchEmailException;
import org.example.global.exception.NoSuchMemberException;
import org.example.global.entity.UsageStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

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
//    private final RedisUtil redisUtil;

    @Transactional
    public EmailAuthenticationResponse sendEmailAuthentication(SendEmailAuthenticationRequest sendEmailAuthenticationRequest){
        // 랜덤 인증 코드 생성
        String authenticationCode = createAuthenticationCode();

        // emailSerivce의 인증코드 보내는 메서드의 성공 여부에 따라 응답
        if (!emailService.sendEmailAuthentication(sendEmailAuthenticationRequest, authenticationCode)){
            throw new InvalidEmailException("인증코드 발송 실패");
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

        return new EmailAuthenticationResponse(memberAuthenticationCodeEntity.getId());

    }

    public String createAuthenticationCode(){
        return RandomStringUtils.random(8, true, true);
    }

    @Transactional
    public EmailAuthenticationResponse authenticateCode(AuthenticateCodeRequest authenticateCodeRequest){
        // 유효한 인증 코드 데이터를 찾아서
        Optional<MemberAuthenticationCodeEntity> memberAuthenticationCodeEntityOptional =
                memberAuthenticationCodeRepository.findByEmailAndEndDateAfterAndStatusEquals(authenticateCodeRequest.getEmail(), LocalDateTime.now(), UsageStatus.ACTIVE);

        // 없으면 인증 코드 없음 반환
        if (memberAuthenticationCodeEntityOptional.isEmpty()){
            throw new InvalidAccessException("인증번호가 아닙니다.");
        }

        // 있으면 찾아서
        MemberAuthenticationCodeEntity memberAuthenticationCodeEntity = memberAuthenticationCodeEntityOptional.get();

        // 입력한 인증 코드가 일치하는 지 검증
        if (memberAuthenticationCodeEntity.getCode().equals(authenticateCodeRequest.getCode())){
            memberAuthenticationCodeEntity.authenticateEmail();
            memberAuthenticationCodeEntity.deleteCode();

            return new EmailAuthenticationResponse(memberAuthenticationCodeEntity.getId());
        } else {
            throw new InvalidAccessException("인증에 실패하였습니다.");
        }
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest){
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> {
           throw new NoSuchEmailException();
        });

        if(member.getStatus().equals(UsageStatus.DELETED)){
            throw new NoSuchMemberException();
        }

        if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())){
            throw new InvalidAccessException("로그인에 실패하셨습니다.");
        } else{
            String accessToken = jwtTokenProvider.createToken(member.getId());
            member.updateLastLogin();
            return new LoginResponse(member.getId(), accessToken);
        }
    }

//    public String logout(LogoutRequest logoutRequest){
//        redisUtil.setBlackList(logoutRequest.getToken(), "accessToken", 5);
//        return "로그아웃 완료";
//    }

}
