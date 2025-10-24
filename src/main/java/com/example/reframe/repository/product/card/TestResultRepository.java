package com.example.reframe.repository.product.card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reframe.entity.product.card.CardTestResult;

public interface TestResultRepository extends JpaRepository<CardTestResult,Long>{

	
	 @Query("SELECT c.resultType, c.count "
	 	  + "FROM CardTestResult c")
	 List<Object[]> findResultTypeCounts();

}
