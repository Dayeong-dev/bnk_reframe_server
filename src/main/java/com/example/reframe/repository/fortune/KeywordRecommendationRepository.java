package com.example.reframe.repository.fortune;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.fortune.KeywordRecommendation;
import com.example.reframe.enums.FortuneKeyword;

public interface KeywordRecommendationRepository extends JpaRepository<KeywordRecommendation, Long> {
    Optional<KeywordRecommendation> findByKeyword(FortuneKeyword keyword);
}
