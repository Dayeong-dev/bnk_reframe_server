package com.example.reframe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.FortuneKeyword;
import com.example.reframe.entity.KeywordRecommendation;

public interface KeywordRecommendationRepository extends JpaRepository<KeywordRecommendation, Long> {
    Optional<KeywordRecommendation> findByKeyword(FortuneKeyword keyword);
}
