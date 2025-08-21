package com.example.reframe.repository.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	List<User> findByUsertype(String string);

	List<User> findByRole(String role);


    // 특정 사용자만 조회 (연령/성별 계산에 사용 가능)
    @Query("""
        select u from User u
         where u.id in :ids
    """)
    List<User> findAllByIdIn(@Param("ids") List<Long> ids);
}