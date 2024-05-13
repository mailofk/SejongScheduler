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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JsoupService {

    private final TitleInfoRepository titleInfoRepository;

    public List<TitleInfo> getTitleInfos(String username, String password) throws IOException {

        Document doc = getDocument(username, password, "https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming");

//        Elements tasks = doc.select("div.calendar-name-date");
        Elements tasks = doc.select("div.event");

        Elements subjectNames = doc.select("select.custom-select option");
        List<TitleInfo> titleInfos = new ArrayList<>();

        for (Element task : tasks) {
            String title = task.select("div.calendar-name").text();
            String date = task.select("div.calendar-date").text();

            String subjectNum = task.attr("data-course-id");
            //구성요소 "속성이름"에 대한 값을 반환

            String subject = subjectNames.select("option[value=" + subjectNum + "]").text();

            TitleInfo titleInfo = new TitleInfo(title, date, subject);

            titleInfoRepository.save(titleInfo);
            titleInfos.add(titleInfo);
        }

        return titleInfos;
    }

    public List<TitleInfo> getExistTitleInfos() {

        return titleInfoRepository.findAll();
    }

    public void deleteTitleInfos() {
        titleInfoRepository.deleteAll();
    }

    public String getMonthTable() throws IOException {
        String thisMonth = LocalDate.now().getMonthValue() + "월";

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

        Document doc = Jsoup.connect("http://sejong.ac.kr/unilife/program_01.html")
                .get();
        Elements everyMonthTable = doc.select("div.calendar_wrap");
        Elements thisMonthList = null;

        for (Element monthTable : everyMonthTable) {
            String monthTitle = monthTable.select("h4.cal_title").text();
            if (monthTitle.contains(thisMonth)) {
                thisMonthList = monthTable.select("div.calendar_list");
                break;
            }
        }

        Elements monthTasks = thisMonthList.select("li");

        List<MonthListForm> monthData = new ArrayList<>();
        for (Element monthTask : monthTasks) {
            String date = monthTask.select("span").text();
            String title = monthTask.text().replace(date, "").trim();

            monthData.add(new MonthListForm(date, title));
        }

        return monthData;
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
