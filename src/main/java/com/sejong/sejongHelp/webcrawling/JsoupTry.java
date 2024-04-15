package com.sejong.sejongHelp.webcrawling;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class JsoupTry {

    public static void main(String[] args) throws IOException {

        Connection.Response res2 = Jsoup.connect("https://ecampus.sejong.ac.kr/login/index.php")
                .data("username", "21011859")
                .data("password", "rnjsgusdn99@")
                .method(Connection.Method.POST)
                .execute();

        Map<String, String> cookies = res2.cookies();

        Document doc = Jsoup.connect("https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming")
                .cookies(cookies)
                .get();


        Elements titles = doc.select("h3.mb-0");
        Elements dueDates = doc.select("span.date-link");
        Elements keke = doc.select("div.calendar-name-date");


        for (Element k : keke) {
            Elements t1 = k.select("div.calendar-name");
            Elements d1 = k.select("div.calendar-date");

            System.out.println("제목 :" + t1.text());
            System.out.println("날짜 :" + d1.text());

        }
    }

}
