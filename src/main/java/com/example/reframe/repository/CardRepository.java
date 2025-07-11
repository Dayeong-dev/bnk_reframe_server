package com.example.reframe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

    // 메인페이지: 조회수 상위 6개
    @Query(value = "SELECT * FROM CARD ORDER BY view_count DESC FETCH FIRST 6 ROWS ONLY", nativeQuery = true)
    List<Card> findTop6();

    // 상세페이지: 카드 + 소분류까지 페치 조인
    @Query("SELECT c FROM Card c LEFT JOIN FETCH c.categoryRels cr LEFT JOIN FETCH cr.subcategory WHERE c.cardId = :cardId")
    Optional<Card> findByIdWithCategories(@Param("cardId") Long cardId);

	List<Card> findByCategoryMajor(String categoryMajor);

	List<Card> findByStatus(String status);

	List<Card> findByCategoryMajorAndStatus(String categoryMajor, String status);
    
    
    
}