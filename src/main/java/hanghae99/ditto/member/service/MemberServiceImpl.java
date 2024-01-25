package hanghae99.ditto.member.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberJoinResponse saveMember(MemberJoinRequest memberJoinRequest){

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
