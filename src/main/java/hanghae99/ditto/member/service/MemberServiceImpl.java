package hanghae99.ditto.member.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import hanghae99.ditto.member.exception.NoSuchMemberException;
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
        String pwd = memberJoinRequest.getPassword();
        pwd = bCryptPasswordEncoder.encode(pwd);

        Member member = new Member(memberJoinRequest.getEmail(), pwd, memberJoinRequest.getMemberName(),
                memberJoinRequest.getProfileImage(), memberJoinRequest.getBio(), LocalDateTime.now());

        Member savedMember = memberRepository.save(member);
        return new MemberJoinResponse(savedMember);
    }

    public Member findByEmail(final String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);
    }

    public boolean existsByEmail(String email){
        return memberRepository.existsByEmail(email);
    }
}
