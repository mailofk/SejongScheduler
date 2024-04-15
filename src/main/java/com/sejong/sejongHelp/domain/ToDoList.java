package com.sejong.sejongHelp.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity
@Getter
@Setter
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todolist_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public ToDoList() {
    }

    @Builder
    public ToDoList(String title, String description, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
}
