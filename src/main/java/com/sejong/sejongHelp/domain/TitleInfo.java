package com.sejong.sejongHelp.domain;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TitleInfo {

    private static long sequence = 0L;

    private long id;
    private String title;
    private String dueDate;

    public TitleInfo(String title, String dueDate) {
        this.id = ++sequence;
        this.title = title;
        this.dueDate = dueDate;
    }



}
