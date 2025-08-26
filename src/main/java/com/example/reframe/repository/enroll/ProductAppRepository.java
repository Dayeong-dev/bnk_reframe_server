package com.example.reframe.repository.enroll;

import com.example.reframe.domain.enroll.EnrollProductApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAppRepository extends JpaRepository<EnrollProductApplication, Long> {

    // 총 신청 건수 (row count)
    @Query(value = """
        SELECT COUNT(*) 
          FROM PRODUCT_APPLICATION 
         WHERE PRODUCT_ID = :productId
        """, nativeQuery = true)
    long countApplicationsByProductId(@Param("productId") Long productId);

    // 고유 사용자 수 (USER_ID 중복 제거)
    @Query(value = """
        SELECT COUNT(DISTINCT USER_ID) 
          FROM PRODUCT_APPLICATION 
         WHERE PRODUCT_ID = :productId
        """, nativeQuery = true)
    long countDistinctUsersByProductId(@Param("productId") Long productId);

    // 상태별 카운트
    @Query(value = """
        SELECT STATUS, COUNT(*) 
          FROM PRODUCT_APPLICATION 
         WHERE PRODUCT_ID = :productId
         GROUP BY STATUS
        """, nativeQuery = true)
    List<Object[]> groupByStatus(@Param("productId") Long productId);
 // 가입자수 기준 TOP N 상품
    @Query(value = """
        SELECT PRODUCT_ID, COUNT(DISTINCT USER_ID) AS USER_COUNT
          FROM PRODUCT_APPLICATION
         GROUP BY PRODUCT_ID
         ORDER BY USER_COUNT DESC
         FETCH FIRST :limit ROWS ONLY
        """, nativeQuery = true)
    List<Object[]> findTopProducts(@Param("limit") int limit);

}
