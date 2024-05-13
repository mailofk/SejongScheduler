package com.sejong.sejongHelp.service;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.domain.TitleInfo;
import com.sejong.sejongHelp.dto.MonthListForm;
import com.sejong.sejongHelp.repository.TitleInfoRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JsoupService {

    private final TitleInfoRepository titleInfoRepository;
    private final CourseService courseService;

    public List<TitleInfo> getTitleInfos(String username, String password) throws IOException {

        HashMap<String, Integer> courseMap = courseService.getCourseMap();

        List<Course> courseList = courseService.getCourseList();


        Document doc = getDocument(username, password, "https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming");

        Elements tasks = doc.select("div.event");

        Elements subjectNames = doc.select("select.custom-select option");
        List<TitleInfo> titleInfos = new ArrayList<>();

        for (Element task : tasks) {
            String title = task.select("div.calendar-name").text();
            String date = task.select("div.calendar-date").text();

            String subjectNum = task.attr("data-course-id");
            //구성요소 "속성이름"에 대한 값을 반환

            String subject = subjectNames.select("option[value=" + subjectNum + "]").text();
            //해당하는 과목의 value에 1씩 더해줌 (기본값은 0부터 시작)
            Integer taskNum = courseMap.get(subject);
            courseMap.put(subject, taskNum + 1);

            TitleInfo titleInfo = new TitleInfo(title, date, subject);

            titleInfoRepository.save(titleInfo);
            titleInfos.add(titleInfo);
        }

        //최종 과목 일정 개수를 해당하는 과목 정보에 추가
        for (Course course : courseList) {
            Integer totalTaskNum = courseMap.get(course.getTitle());
            course.setTaskCount(totalTaskNum);
        }

        return titleInfos;
    }

    public List<TitleInfo> getExistTitleInfos() {

        return titleInfoRepository.findAll();
    }

    public void deleteTitleInfos() {
        titleInfoRepository.deleteAll();
    }

    //이전 달 & 다음 달 정보 포함
    public String getMonthTable() throws IOException {
        String thisMonth = LocalDate.now().getMonthValue() + "월";

        String lastMonth = (LocalDate.now().getMonthValue() - 1) + "월";
        String nextMonth = (LocalDate.now().getMonthValue() + 1) + "월";

        Document doc = Jsoup.connect("http://sejong.ac.kr/unilife/program_01.html")
                .get();
        Elements everyMonthTable = doc.select("div.calendar_wrap");
        Elements thisMonthTable = null;

        for (Element monthTable : everyMonthTable) {
            String monthTitle = monthTable.select("h4.cal_title").text();
            if (monthTitle.contains(thisMonth)) {
                thisMonthTable = monthTable.select("div.calendar");
                break;
            }
        }

        return thisMonthTable.toString();
    }

    public List<MonthListForm> getMonthList() throws IOException {
        String thisMonth = LocalDate.now().getMonthValue() + "월";
        String lastMonth = LocalDate.now().getMonthValue() - 1 + "월";
        String nextMonth = LocalDate.now().getMonthValue() + 1 + "월";

        Document doc = Jsoup.connect("http://sejong.ac.kr/unilife/program_01.html")
                .get();
        Elements everyMonthTable = doc.select("div.calendar_wrap");

        List<MonthListForm> monthData = new ArrayList<>();
        //특정 월의 일정 추가해주는 메서드
        //저번달, 이번달, 다음달 순서대로 리스트에 추가되도록 설정

        getAllMonthList(monthData, everyMonthTable, lastMonth);
        getAllMonthList(monthData, everyMonthTable, thisMonth);
        getAllMonthList(monthData, everyMonthTable, nextMonth);

        return monthData;
    }

    private static void getAllMonthList(List<MonthListForm> monthData, Elements everyMonthTable, String month) {
        if (month.contains("13월") || month.contains("0월")) {
            return;
        }
        Elements allMonthList = null;

        for (Element monthTable : everyMonthTable) {
            String monthTitle = monthTable.select("h4.cal_title").text();
            if (monthTitle.contains(month)) {
                allMonthList = monthTable.select("div.calendar_list");
                break;
            }
        }
        Elements monthTasks = allMonthList.select("li");

        for (Element monthTask : monthTasks) {
            String date = month + " " + monthTask.select("span").text();
            String title = monthTask.text().replace(date, "").trim();

            monthData.add(new MonthListForm(date, title));
        }
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
