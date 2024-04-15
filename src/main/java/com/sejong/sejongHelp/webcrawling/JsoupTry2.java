package com.sejong.sejongHelp.webcrawling;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JsoupTry2 {

    public static void main(String[] args) throws IOException {
//        JsoupTry.getSite();
//        JsoupTry.getCookie();
//        JsoupTry.CookieTest();


        Connection.Response res2 = Jsoup.connect("https://www.kmooc.kr/login?tm=20240317005839")
                .data("userid", "mailofk@naver.com")
//                .data("ssoGubun", "Login")
//                .data("type", "popup_login")
                .method(Connection.Method.POST)
                .execute();

        Map<String, String> cookies = res2.cookies();

        Document doc = Jsoup.connect("https://lms.kmooc.kr/")
                .cookies(cookies)
//                .timeout(10000)
                .get();

        System.out.println(doc.body());


        Elements titles = doc.select("a.coursefullname");
        for (Element title : titles) {
            System.out.println(title);
        }
    }
    public static void getSite() throws IOException {
        Document document = Jsoup.connect("https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming").get();
        Elements titles = document.select("h3.mb-0");

        int cnt = 0;

        for (Element title : titles) {
            System.out.println(title);
            cnt++;
        }
        System.out.println("======================");
        System.out.println(cnt);
    }

    public static void CookieTest() throws IOException {
        Connection.Response res2 = Jsoup.connect("https://ecampus.sejong.ac.kr/login.php")
                .data("username","21011859", "password", "rnjsgusdn99@")
                .method(Connection.Method.POST)
                .timeout(10000)
                .execute();

        Map<String, String> cookies = res2.cookies();

        Document doc = Jsoup.connect("https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming")
                .cookies(cookies)
                .timeout(10000)
                .get();

        Elements titles = doc.select("h3");
        for (Element title : titles) {
            System.out.println(title);
        }
    }

    public static void getCookie() throws IOException {
        Connection.Response loginPageResponse = Jsoup.connect("https://ecampus.sejong.ac.kr/login.php")
                .timeout(3000)
                .header("Origin", "https://ecampus.sejong.ac.kr/")
                .header("Referer", "https://ecampus.sejong.ac.kr/login.php")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate, br, zstd")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .method(Connection.Method.GET)
                .execute();

        Map<String, String> cookies = loginPageResponse.cookies();

        Connection.Response response = Jsoup.connect("https://ecampus.sejong.ac.kr/login.php")
                .data("username","21011859", "password", "rnjsgusdn993@")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .cookie("MoodleSession", loginPageResponse.cookies().get("MoodleSession"))
                .method(Connection.Method.POST)
                .timeout(5000)
                .execute();

        System.out.println(response.cookies());
        System.out.println("=========cookie 끝============");

        Document doc = Jsoup.connect("https://ecampus.sejong.ac.kr/calendar/view.php?view=upcoming").cookies(response.cookies()).get();
        Elements titles = doc.select("input.form-control");

//        System.out.println(doc);

        int cnt = 0;

        for (Element title : titles) {
            System.out.println(title);
            cnt++;
        }
        System.out.println("======================");
        System.out.println(cnt);
    }
}
