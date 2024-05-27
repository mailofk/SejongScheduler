package com.sejong.sejongHelp.service;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.dto.CourseForm;
import com.sejong.sejongHelp.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course save(CourseForm courseForm) {
        Course course = new Course(courseForm.getTitle());
        return courseRepository.save(course);
    }

    public void deleteAll() {
        courseRepository.deleteAll();
    }

    public List<Course> getCourseList() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseTitle(String username, String password) throws IOException {

        Document doc = getDocument(username, password, "https://ecampus.sejong.ac.kr/dashboard.php");

        Elements courseTitles = doc.select("div.course-title h3");

        List<Course> courseTitleList = new ArrayList<>();

        for (Element courseTitle : courseTitles) {
            String title = courseTitle.ownText();
            if (!title.startsWith("[")) {
                title = title.split(" ")[0];
            } else {
                title = title.split(" \\(\\(LEARNING")[0];
            }

            Course newCourse = new Course(title);

            courseTitleList.add(newCourse);
            courseRepository.save(newCourse);
        }

        return courseTitleList;
    }


    private static Document getDocument(String username, String password, String url) throws IOException {
        Connection.Response res2 = Jsoup.connect("https://ecampus.sejong.ac.kr/login/index.php")
                .data("username", username)
                .data("password", password)
                .method(Connection.Method.POST)
                .execute();

        Map<String, String> cookies = res2.cookies();

        Document doc = Jsoup.connect(url)
                .cookies(cookies)
                .get();
        return doc;
    }
}
