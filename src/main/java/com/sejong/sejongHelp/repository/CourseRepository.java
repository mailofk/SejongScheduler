package com.sejong.sejongHelp.repository;

import com.sejong.sejongHelp.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    void deleteAllByUser(String user);
}
