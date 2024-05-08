package org.example.repository;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.example.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("org.example.domain.Member")
public interface MemberMapper {
    Member selectById(String no);

    @MapKey("id")
    List<Map<String, Object>> findMembers();

    int insert(Member member);

    int updatePassword(Long id, String password);

    int deleteMember(String id);
}
