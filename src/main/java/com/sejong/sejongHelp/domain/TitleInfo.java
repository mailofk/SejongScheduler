package com.sejong.sejongHelp.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class TitleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "titleInfo_id")
    private long id;

    private String title;
    private String dueDate;
    private String subject;
    private String type;

    private String user;

    public TitleInfo() {
    }

    public TitleInfo(String title, String dueDate, String subject,String type, String user) {
        this.title = title;
        this.dueDate = dueDate;
        this.subject = subject;
        this.type = type;
        this.user = user;
    }



}
