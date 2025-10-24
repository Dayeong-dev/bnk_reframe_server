package com.example.reframe.repository.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.customer.Qna;

public interface QnaRepository extends JpaRepository<Qna, Integer>{

	List<Qna> findByUserId(Long id);
	Optional<Qna> findByQnaIdAndUserId(Integer qnaId, Long userId);
	
	
    @Query("select q from Qna q join fetch q.user u order by q.qnaId desc")
    List<Qna> findAllWithUser();

    @Query("select q from Qna q join fetch q.user u where q.qnaId = :id")
    Optional<Qna> findByIdWithUser(@Param("id") Integer id);
	
}
