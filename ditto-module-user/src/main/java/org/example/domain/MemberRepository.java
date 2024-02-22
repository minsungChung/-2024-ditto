package org.example.domain;

import org.example.global.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    default Member getById(Long id){
        return findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }

    default Member getByEmail(String email){
        return findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);
    }
}
