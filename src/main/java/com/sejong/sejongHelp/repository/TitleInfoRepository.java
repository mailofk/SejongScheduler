package com.sejong.sejongHelp.repository;

import com.sejong.sejongHelp.domain.TitleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleInfoRepository extends JpaRepository<TitleInfo, Long> {

    List<TitleInfo> findAllByUser(String user);

    void deleteAllByUser(String user);
}
