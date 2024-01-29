package hanghae99.ditto.member.service;

import hanghae99.ditto.auth.domain.MemberAuthenticationCodeRepository;
import hanghae99.ditto.auth.exception.InvalidAccessException;
import hanghae99.ditto.auth.exception.InvalidEmailException;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.dto.request.MemberInfoRequest;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.request.MemberPasswordRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import hanghae99.ditto.member.dto.response.UpdateMemberResponse;
import hanghae99.ditto.member.exception.NoSuchMemberException;
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
            throw new InvalidEmailException();
        });

        if (memberRepository.existsByEmail(memberJoinRequest.getEmail())){
            throw new InvalidEmailException("이미 존재하는 이메일입니다.");
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
    public UpdateMemberResponse updateMemberInfo(Member member, Long memberId, MemberInfoRequest memberInfoRequest){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });

        // 인가된 이메일과 바꾸려는 정보의 이메일이 동일하면
        if (member.equals(findMember)){
            member.updateMemberExtraInfo(memberInfoRequest.getMemberName(), memberInfoRequest.getProfileImage(), memberInfoRequest.getBio());
        } else {
            throw new InvalidAccessException();
        }

        return new UpdateMemberResponse(memberId);
    }

    @Transactional
    public UpdateMemberResponse updateMemberPassword(Member member, Long memberId, MemberPasswordRequest memberPasswordRequest){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });

        if (member.equals(findMember)){
            String newPassword = memberPasswordRequest.getNewPassword();
            // 기존 비밀번호와 일치하면
            if (bCryptPasswordEncoder.matches(newPassword, member.getPassword())){
                throw new RuntimeException("기존 비밀번호와 동일한 비밀번호입니다.");
            } else {
                member.updateMemberPassword(bCryptPasswordEncoder.encode(newPassword));
            }
        } else {
            throw new InvalidAccessException();
        }

        return new UpdateMemberResponse(memberId);
    }
}
