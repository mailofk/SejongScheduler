package com.sejong.sejongHelp.service;

import com.sejong.sejongHelp.domain.Member;
import com.sejong.sejongHelp.dto.CustomUserDetails;
import com.sejong.sejongHelp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {


        Member member = memberRepository.findByStudentId(studentId);

        if (member != null) {
            return new CustomUserDetails(member);
        }

        return null;
    }
}
