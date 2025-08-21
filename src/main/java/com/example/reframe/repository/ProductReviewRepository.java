// src/main/java/com/example/reframe/repository/ProductReviewRepository.java
package com.example.reframe.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    List<ProductReview> findByProduct_ProductIdOrderByIdDesc(Long productId);

    // 목록 조회시 사용자까지 함께 로딩(지연로딩 예외 방지)
    @Query("""
        select r from ProductReview r
        join fetch r.user u
        join fetch r.product p
        where p.productId = :productId
        order by r.id desc
    """)
    List<ProductReview> findAllByProductIdWithUser(@Param("productId") Long productId);

    // 기간 평균 별점 (rating not null)
    @Query("""
        select avg(r.rating)
          from ProductReview r
         where r.rating is not null
           and r.createdAt between :from and :to
    """)
    Double avgRatingIn(@Param("from") Date from, @Param("to") Date to);

    // 기간 별점 분포 (별점, 개수)
    @Query("""
        select r.rating, count(r.id)
          from ProductReview r
         where r.rating is not null
           and r.createdAt between :from and :to
         group by r.rating
         order by r.rating
    """)
    List<Object[]> ratingDistIn(@Param("from") Date from, @Param("to") Date to);

    // 기간 내 긍/부정 카운트
    @Query("""
        select r.negative, count(r.id)
          from ProductReview r
         where r.createdAt between :from and :to
         group by r.negative
    """)
    List<Object[]> sentimentCountsIn(@Param("from") Date from, @Param("to") Date to);

    // 기간 내 리뷰 수 기준 상품 TOP
    @Query("""
        select r.product.productId, count(r.id)
          from ProductReview r
         where r.createdAt between :from and :to
         group by r.product.productId
         order by count(r.id) desc
    """)
    List<Object[]> topProductsByReviewIn(@Param("from") Date from, @Param("to") Date to);
}
