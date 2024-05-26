package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.dto.CustomUserDetails;
import com.sejong.sejongHelp.dto.MainInfoForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
public class LoginFormController {

    @GetMapping("/main")
    public MainInfoForm mainPage(@AuthenticationPrincipal CustomUserDetails member) throws IOException {

        MainInfoForm mainInfoForm = new MainInfoForm();
        mainInfoForm.setName(member.getMemberName());
        mainInfoForm.setMajor(member.getMajor());
        mainInfoForm.setStudentId(member.getUsername());

        return mainInfoForm;
    }

}
