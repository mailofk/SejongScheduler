package com.sejong.sejongHelp.service;

import com.sejong.sejongHelp.domain.Member;
import com.sejong.sejongHelp.dto.JoinForm;
import com.sejong.sejongHelp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //임시수정 --> 모두 수정한 후 제거하는 것이 목표
//    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Member save(JoinForm joinForm) {
        Member member = new Member(joinForm.getStudentId(),
                bCryptPasswordEncoder.encode(joinForm.getPassword()),
                joinForm.getMajor(), joinForm.getName());

        return memberRepository.save(member);
    }

}
