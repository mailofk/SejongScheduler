package com.sejong.sejongHelp.service;

import com.sejong.sejongHelp.domain.Member;
import com.sejong.sejongHelp.domain.ToDoList;
import com.sejong.sejongHelp.dto.JoinForm;
import com.sejong.sejongHelp.dto.ToDoListForm;
import com.sejong.sejongHelp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member save(JoinForm joinForm) {
        Member member = new Member(joinForm.getStudentId(),
                bCryptPasswordEncoder.encode(joinForm.getPassword()),
                joinForm.getMajor(), joinForm.getName());

        return memberRepository.save(member);
    }

//    public void addToDoList(Long memberId, ToDoList toDoList) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//
//        member.setToDoList(toDoList);
//    }
//
//    public List<ToDoListForm> findLists(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//
//        List<ToDoList> toDoLists = member.getToDoLists();
//
//        List<ToDoListForm> listForms = new ArrayList<>();
//        for (ToDoList toDoList : toDoLists) {
//            ToDoListForm listForm = new ToDoListForm();
//            listForm.setTitle(toDoList.getTitle());
//            listForm.setDescription(toDoList.getDescription());
//
//            listForms.add(listForm);
//        }
//
//        return listForms;
//    }
}
