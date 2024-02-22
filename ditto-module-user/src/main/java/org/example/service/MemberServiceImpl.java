package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.MemberAuthenticationCodeRepository;
import org.example.global.dto.MemberDto;
import org.example.global.exception.InvalidAccessException;
import org.example.global.exception.InvalidEmailException;
import org.example.domain.Member;
import org.example.domain.MemberRepository;
import org.example.dto.request.MemberInfoRequest;
import org.example.dto.request.MemberJoinRequest;
import org.example.dto.request.MemberPasswordRequest;
import org.example.dto.response.MemberJoinResponse;
import org.example.dto.response.UpdateMemberResponse;
import org.example.global.exception.NoSuchMemberException;
import org.example.global.exception.SamePasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
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

    public MemberDto getOneMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });
        return MemberDto.builder()
                .memberId(member.getId())
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .bio(member.getBio())
                .status(member.getStatus()).build();
    }

    @Transactional
    public UpdateMemberResponse updateMemberInfo(Long myId, Long memberId, MemberInfoRequest memberInfoRequest){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });

        // 인가된 이메일과 바꾸려는 정보의 이메일이 동일하면
        if (myId == findMember.getId()){
            findMember.updateMemberExtraInfo(memberInfoRequest.getMemberName(), memberInfoRequest.getProfileImage(), memberInfoRequest.getBio());
        } else {
            throw new InvalidAccessException();
        }

        return new UpdateMemberResponse(memberId);
    }

    @Transactional
    public UpdateMemberResponse updateMemberPassword(Long myId, Long memberId, MemberPasswordRequest memberPasswordRequest){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });

        if (myId == findMember.getId()){
            String newPassword = memberPasswordRequest.getNewPassword();
            // 기존 비밀번호와 일치하면
            if (bCryptPasswordEncoder.matches(newPassword, findMember.getPassword())){
                throw new SamePasswordException();
            } else {
                findMember.updateMemberPassword(bCryptPasswordEncoder.encode(newPassword));
            }
        } else {
            throw new InvalidAccessException();
        }

        return new UpdateMemberResponse(memberId);
    }
}
