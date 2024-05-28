package com.sejong.sejongHelp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private long id;

    @Column(nullable = false)
    private String title;

    private int taskCount;

    private String user;

    public Course() {

    }

    public Course(String title, String user) {
        this.taskCount = 0;
        this.title = title;
        this.user = user;
    }
}
