package com.zerock.apiserver.repository;

import com.zerock.apiserver.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {
    // @ElementCollection 으로 memberRole에 대해 테이블 따로 관리 중인데
    // 멤버 조회할때는 role도 같이 가져와야하니 @EntityGraph를 이용해서 memberRoleList도 참조해서 가져오게 한다  
    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Member getWithRoles(@Param("email") String email);
}
