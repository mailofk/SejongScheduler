package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.ToDoList;
import com.sejong.sejongHelp.dto.CustomUserDetails;
import com.sejong.sejongHelp.dto.ToDoListForm;
import com.sejong.sejongHelp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
//@RequiredArgsConstructor
//public class ToDoListController {
//
//    private final ToDoListService toDoListService;
//
//    @PostMapping("/api/list")
//    @ResponseBody
//    public ResponseEntity<ToDoListForm> makeOne(@RequestBody ToDoListForm toDoListForm, @AuthenticationPrincipal CustomUserDetails member) {
//        ToDoList toDoList = toDoListService.save(toDoListForm, member.getMemberId());
//
//        return new ResponseEntity<>(toDoListForm, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/add-list")
//    public String addList() {
//        return "addToDoList";
//    }
//}
