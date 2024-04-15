package com.sejong.sejongHelp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ToDoListForm {

    private String title;
    private String description;
    private LocalDateTime dueDate;
}
