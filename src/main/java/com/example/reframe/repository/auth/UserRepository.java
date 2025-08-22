package com.example.reframe.repository.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.account.AccountStatus;
import com.example.reframe.entity.account.AccountType;
import com.example.reframe.entity.auth.User;
import com.example.reframe.repository.GenderCountView;

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
    
    @Query("""
            select u.gender as gender, count(u.id) as count
              from User u
             where exists (
                    select 1
                      from Account a
                     where a.user = u
                       and a.accountType = :productType
                       and a.status = :activeStatus
                )
             group by u.gender
            """)
        List<GenderCountView> countUsersByGenderForActiveProductHolders(@Param("productType") AccountType productType,
        																@Param("activeStatus") AccountStatus activeStatus);
}