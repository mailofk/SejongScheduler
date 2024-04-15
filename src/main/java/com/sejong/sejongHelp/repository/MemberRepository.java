package com.sejong.sejongHelp.repository;

import com.sejong.sejongHelp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByStudentId(String studentId);
}
