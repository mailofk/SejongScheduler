package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.domain.TitleInfo;
import com.sejong.sejongHelp.dto.MemberForm;
import com.sejong.sejongHelp.service.JsoupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JsoupController {

    private final JsoupService jsoupService;

    @PostMapping("/title")
    public List<TitleInfo> makeIt(@RequestBody MemberForm memberForm) throws IOException {
        //폼 만들기 전까지는 임시로 직접 설정 (나중에 @ModelAttribute 등으로 파라미터 설정)
//        String username = "21011859";
//        String password = "rnjsgusdn99@";

        String username = memberForm.getStudentId();
        String password = memberForm.getPassword();

        return jsoupService.getTitleInfos(username, password);
    }

    @GetMapping("/month-table")
    public String makeMonthTable() throws IOException {
        String header = "<!DOCTYPE html><html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>달력 테스트</title>" +
                "<style>" +
                ".cal_red, .cal_red_on {color: #f70000;}" +
                ".cal_blue, .cal_blue_on {color: #0000f7;}" +
                ".on {background-color: #71d9d1;}" +
                "</style>" +
                "</head>" +
                "<body>";
        String end = "</body></html>";

        return header + jsoupService.getMonthTable() + end;
    }

    @GetMapping("/month-list")
    public String makeMonthList() throws IOException {
        return jsoupService.getMonthList();
    }
}
