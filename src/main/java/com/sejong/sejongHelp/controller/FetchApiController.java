package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.domain.TitleInfo;
import com.sejong.sejongHelp.dto.MemberForm;
import com.sejong.sejongHelp.dto.MonthListForm;
import com.sejong.sejongHelp.service.CourseService;
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
public class FetchApiController {

    private final JsoupService jsoupService;
    private final CourseService courseService;

    //과목 제목 크롤링 메서드
    @PostMapping("/api/title")
    public List<Course> getSubjectTitle(@RequestBody MemberForm memberForm) throws IOException {
        courseService.deleteAll();
        return courseService.getCourseTitle(memberForm.getStudentId(), memberForm.getPassword());
    }

    //과목별 일정 크롤링 메서드
    @PostMapping("/api/subject")
    public List<TitleInfo> getSubjectInfo(@RequestBody MemberForm memberForm) throws IOException {
        jsoupService.deleteTitleInfos();
        return jsoupService.getTitleInfos(memberForm.getStudentId(), memberForm.getPassword());
    }

    //세종 학사 정보 가져오기
    @GetMapping("/api/month")
    public List<MonthListForm> getSejongSchedule() throws IOException {
        return jsoupService.getMonthList();
    }


}

