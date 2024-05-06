package com.sejong.sejongHelp.service;

import com.sejong.sejongHelp.domain.Course;
import com.sejong.sejongHelp.domain.TitleInfo;
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

    public List<TitleInfo> getTitleInfos(String username, String password) throws IOException {

        Document doc = getDocument(username, password, "https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming");

        Elements tasks = doc.select("div.calendar-name-date");
        List<TitleInfo> titleInfos = new ArrayList<>();

        for (Element task : tasks) {
            String title = task.select("div.calendar-name").text();
            String date = task.select("div.calendar-date").text();

            titleInfos.add(new TitleInfo(title, date));
        }

        return titleInfos;
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

    public String getMonthList() throws IOException {
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

        return thisMonthList.toString();
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
