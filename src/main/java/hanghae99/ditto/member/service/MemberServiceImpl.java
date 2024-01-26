package hanghae99.ditto.member.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.dto.request.MemberInfoRequest;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.request.MemberPasswordRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberJoinResponse saveMember(MemberJoinRequest memberJoinRequest){

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
    public void updateMemberInfo(Long memberId, MemberInfoRequest memberInfoRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 아이디입니다.");
        });
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 인가된 이메일과 바꾸려는 정보의 이메일이 동일하면
        if (userDetails.getUsername().equals(member.getEmail())){
            member.updateMemberExtraInfo(memberInfoRequest.getMemberName(), memberInfoRequest.getProfileImage(), memberInfoRequest.getBio());
        } else {
            throw new RuntimeException("인가된 정보와 일치하지 않습니다.");
        }
    }

    @Transactional
    public void updateMemberPassword(Long memberId, MemberPasswordRequest memberPasswordRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 아이디입니다.");
        });
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (member.getPassword().equals(userDetails.getPassword())){
            String newPassword = memberPasswordRequest.getNewPassword();
            // 기존 비밀번호와 일치하면
            if (bCryptPasswordEncoder.matches(newPassword, userDetails.getPassword())){
                throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호입니다.");
            } else {
                member.updateMemberPassword(bCryptPasswordEncoder.encode(newPassword));
            }
        } else {
            throw new RuntimeException("인가된 정보와 일치하지 않습니다");
        }
    }
}
