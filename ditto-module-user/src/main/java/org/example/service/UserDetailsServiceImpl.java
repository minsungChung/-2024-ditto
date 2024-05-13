package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Member;
import org.example.domain.MemberRepository;
import org.example.domain.UserDetailsImpl;
import org.example.global.exception.NoSuchMemberException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(email);
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        UserDetails userDetails = new UserDetailsImpl(member.getEmail(), member.getPassword(), member.getMemberName(), member.getProfileImage(), member.getBio());
        log.info(userDetails.getUsername());
        log.info(userDetails.getPassword());
        return userDetails;
    }
}
