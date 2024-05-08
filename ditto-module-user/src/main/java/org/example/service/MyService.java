package org.example.service;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.example.domain.Member;
import org.example.dto.request.MemberJoinRequest;
import org.example.dto.request.MemberPasswordRequest;
import org.example.global.exception.InvalidAccessException;
import org.example.global.exception.SamePasswordException;
import org.example.repository.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyService {

    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    private final MemberMapper memberMapper;

    public Member getMember(String no){
        return memberMapper.selectById(no);
    }

    public int saveMember(MemberJoinRequest memberJoinRequest) {
        log.info("여기까지 오케이~~");
        Member member = Member.builder()
                .bio(memberJoinRequest.getBio())
                .memberName(memberJoinRequest.getMemberName())
                .email(memberJoinRequest.getEmail())
                .lastLogin(LocalDateTime.now())
                .password(memberJoinRequest.getPassword())
                .profileImage(memberJoinRequest.getProfileImage())
                .build();
        log.info(member.getMemberName());
        return memberMapper.insert(member);
    }

    public int updateMemberPassword(Long myId, Long memberId, MemberPasswordRequest memberPasswordRequest) {
        Member findMember = memberMapper.selectById(String.valueOf(memberId));

        if (myId == findMember.getId()) {
            String newPassword = memberPasswordRequest.getNewPassword();
            // 기존 비밀번호와 일치하면
            if (newPassword.equals(findMember.getPassword())) {
                throw new SamePasswordException();
            } else {
                return memberMapper.updatePassword(findMember.getId(), newPassword);
            }
        } else {
            throw new InvalidAccessException();
        }
    }

    public int deleteMember(Long myId, Long memberId) {
        Member findMember = memberMapper.selectById(String.valueOf(memberId));

        if (myId == findMember.getId()){
            return memberMapper.deleteMember(String.valueOf(myId));
        } else {
            throw new InvalidAccessException();
        }
    }

    public List<Map<String, Object>> findMembers() {
        return memberMapper.findMembers();
    }
}
