package com.sejong.sejongHelp.controller;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.dto.CourseForm;
import com.sejong.sejongHelp.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/course")
    public List<Course> makeCourseTitle() throws IOException {
        //폼 만들기 전까지는 임시로 직접 설정 (나중에 @ModelAttribute 등으로 파라미터 설정)
        String username = "21011859";
        String password = "rnjsgusdn99@";

        List<Course> courses = courseService.getCourseTitle(username, password);
        //이전에 가져왔던 과목명 모두 초기화 시키기
        courseService.deleteAll();

        for (Course course : courses) {
            courseService.save(new CourseForm(course.getTitle()));
        }

        return courseService.getCourseTitle(username, password);
    }

}
