package com.sejong.sejongHelp.webcrawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;

public class DateCrawlingTest {

    public static void main(String[] args) throws IOException {
        String thisMonth = LocalDate.now().getMonthValue() + "월";

        Document doc = Jsoup.connect("http://sejong.ac.kr/unilife/program_01.html")
                .get();
        Elements everyMonthTable = doc.select("div.calendar_wrap");

        for (Element monthTable : everyMonthTable) {
            String monthTitle = monthTable.select("h4.cal_title").text();
            if (monthTitle.contains(thisMonth)) {
                Elements thisMonthTable = monthTable.select("div.calendar");
                Elements thisMonthList = monthTable.select("div.calendar_list");
            }
        }


    }
}
