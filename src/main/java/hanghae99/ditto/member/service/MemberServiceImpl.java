package hanghae99.ditto.member.service;

import hanghae99.ditto.auth.domain.MemberAuthenticationCodeRepository;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.dto.request.MemberInfoRequest;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.request.MemberPasswordRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberAuthenticationCodeRepository memberAuthenticationCodeRepository;

    public MemberJoinResponse saveMember(MemberJoinRequest memberJoinRequest){
        memberAuthenticationCodeRepository.findByEmailAndIsAuthenticatedIsTrue(memberJoinRequest.getEmail()).orElseThrow(()->{
            throw new IllegalArgumentException("인증되지 않은 이메일은 가입하실 수 없습니다.");
        });

        if (memberRepository.existsByEmail(memberJoinRequest.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }else {
            Member member = Member.builder()
                    .email(memberJoinRequest.getEmail())
                    .password(bCryptPasswordEncoder.encode(memberJoinRequest.getPassword()))
                    .memberName(memberJoinRequest.getMemberName())
                    .profileImage(memberJoinRequest.getProfileImage())
                    .bio(memberJoinRequest.getBio())
                    .lastLogin(LocalDateTime.now()).build();

            Member savedMember = memberRepository.save(member);
            return new MemberJoinResponse(savedMember);
        }
    }

    @Transactional
    public void updateMemberInfo(Member member, Long memberId, MemberInfoRequest memberInfoRequest){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 아이디입니다.");
        });

        // 인가된 이메일과 바꾸려는 정보의 이메일이 동일하면
        if (member.equals(findMember)){
            member.updateMemberExtraInfo(memberInfoRequest.getMemberName(), memberInfoRequest.getProfileImage(), memberInfoRequest.getBio());
        } else {
            throw new RuntimeException("권한이 없는 멤버입니다.");
        }
    }

    @Transactional
    public void updateMemberPassword(Member member, Long memberId, MemberPasswordRequest memberPasswordRequest){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 아이디입니다.");
        });

        if (member.equals(findMember)){
            String newPassword = memberPasswordRequest.getNewPassword();
            // 기존 비밀번호와 일치하면
            if (bCryptPasswordEncoder.matches(newPassword, member.getPassword())){
                throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호입니다.");
            } else {
                member.updateMemberPassword(bCryptPasswordEncoder.encode(newPassword));
            }
        } else {
            throw new RuntimeException("권한이 없는 멤버입니다.");
        }
    }
}
