package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.Member;
import com.sejong.sejongHelp.domain.TitleInfo;
import com.sejong.sejongHelp.domain.ToDoList;
import com.sejong.sejongHelp.dto.CustomUserDetails;
import com.sejong.sejongHelp.dto.MemberForm;
import com.sejong.sejongHelp.dto.ToDoListForm;
import com.sejong.sejongHelp.service.JsoupService;
import com.sejong.sejongHelp.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginFormController {

    private final MemberService memberService;
    private final JsoupService jsoupService;

    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal CustomUserDetails member,
                           @RequestBody(required = false) MemberForm memberForm) throws IOException {

        List<TitleInfo> titleInfos = new ArrayList<>();

        if (memberForm!=null) {
            titleInfos = jsoupService.getTitleInfos(memberForm.getStudentId(), memberForm.getPassword());
        }

        model.addAttribute("studentId", member.getUsername());
        model.addAttribute("titleInfos", titleInfos);

        return "home";
    }

    @GetMapping("/")
    public String firstPage() {
        return "firstPage";
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }

    //테스트용
//    @GetMapping("/lists")
//    public String getList(@AuthenticationPrincipal CustomUserDetails member, Model model) {
//        List<ToDoListForm> lists =  memberService.findLists(member.getMemberId());
//
//        model.addAttribute("lists", lists);
//        return "/lists";
//    }

}
