package com.sejong.sejongHelp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthListForm {

    private String date;
    private String title;

    public MonthListForm(String date, String title) {
        this.date = date;
        this.title = title;
    }
}
