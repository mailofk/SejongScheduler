//package com.sejong.sejongHelp.service;
//
//import com.sejong.sejongHelp.domain.ToDoList;
//import com.sejong.sejongHelp.dto.ToDoListForm;
//import com.sejong.sejongHelp.repository.ToDoListRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
////@Service
////@RequiredArgsConstructor
////public class ToDoListService {
////
////    private final ToDoListRepository toDoListRepository;
////    private final MemberService memberService;
////
////    public ToDoList save(ToDoListForm toDoListForm, Long memberId) {
////        ToDoList toDoList = ToDoList.builder()
////                .title(toDoListForm.getTitle())
////                .description(toDoListForm.getDescription())
////                .dueDate(toDoListForm.getDueDate())
////                .build();
////
////        memberService.addToDoList(memberId, toDoList);
////
////        return toDoListRepository.save(toDoList);
////    }
////
////    public void remove(Long id) {
////        toDoListRepository.deleteById(id);
////    }
//
//}
