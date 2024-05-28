package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.domain.TitleInfo;
import com.sejong.sejongHelp.dto.CustomUserDetails;
import com.sejong.sejongHelp.dto.MainInfoForm;
import com.sejong.sejongHelp.service.CourseService;
import com.sejong.sejongHelp.service.JsoupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginFormController {

    private final CourseService courseService;
    private final JsoupService jsoupService;

    @GetMapping("/main")
    public MainInfoForm mainPage(@AuthenticationPrincipal CustomUserDetails member) throws IOException {

        MainInfoForm mainInfoForm = new MainInfoForm();
        mainInfoForm.setName(member.getMemberName());
        mainInfoForm.setMajor(member.getMajor());
        mainInfoForm.setStudentId(member.getUsername());

        return mainInfoForm;
    }

    @GetMapping("/exist-course")
    public List<Course> existCourse(@AuthenticationPrincipal CustomUserDetails member) {
        return courseService.getCourseList(member.getUsername());
    }

    @GetMapping("/exist-subject")
    public List<TitleInfo> existSubject(@AuthenticationPrincipal CustomUserDetails member) {
        return jsoupService.getExistTitleInfos(member.getUsername());
    }

}
