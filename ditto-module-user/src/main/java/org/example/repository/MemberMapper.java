package org.example.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.domain.Member;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("org.example.domain.Member")
public interface MemberMapper {
    Member selectById(String no);

    int insert(Member member);

    int updatePassword(Long id, String password);

    int deleteMember(String id);
}
