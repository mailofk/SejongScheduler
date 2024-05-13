package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.domain.TitleInfo;
import com.sejong.sejongHelp.dto.CustomUserDetails;
import com.sejong.sejongHelp.dto.MemberForm;
import com.sejong.sejongHelp.dto.MonthListForm;
import com.sejong.sejongHelp.service.CourseService;
import com.sejong.sejongHelp.service.JsoupService;
import com.sejong.sejongHelp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final JsoupService jsoupService;
    private final CourseService courseService;

    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal CustomUserDetails member,
                           @RequestBody(required = false) MemberForm memberForm) throws IOException {

        List<TitleInfo> titleInfos = jsoupService.getExistTitleInfos();
        List<Course> courseList = courseService.getCourseList();

        if (memberForm!=null) {
            jsoupService.deleteTitleInfos();
            courseService.deleteAll();

            courseList = courseService.getCourseTitle(memberForm.getStudentId(), memberForm.getPassword());
            titleInfos = jsoupService.getTitleInfos(memberForm.getStudentId(), memberForm.getPassword());
        }

        List<MonthListForm> monthData = jsoupService.getMonthList();

        model.addAttribute("studentId", member.getUsername());
        model.addAttribute("titleInfos", titleInfos);

        model.addAttribute("monthData", monthData);
        model.addAttribute("courseList", courseList);

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

}
