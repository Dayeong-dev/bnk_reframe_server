package com.example.reframe.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.ProductReview;
import java.util.Date;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{
	List<ProductReview> findByProduct_ProductIdOrderByIdDesc(Long productId);
	 // 기간 평균 별점 (rating not null)
    @Query("""
        select avg(r.rating)
          from ProductReview r
         where r.rating is not null
           and r.createdAt between :from and :to
    """)
    Double avgRatingIn(@Param("from") Date from,
                       @Param("to") Date to);

    // 기간 별점 분포 (별점, 개수)
    @Query("""
        select r.rating, count(r.id)
          from ProductReview r
         where r.rating is not null
           and r.createdAt between :from and :to
         group by r.rating
         order by r.rating
    """)
    List<Object[]> ratingDistIn(@Param("from") Date from,
                                @Param("to") Date to);

    // 기간 내 긍/부정 카운트 (negative boolean)
    @Query("""
        select r.negative, count(r.id)
          from ProductReview r
         where r.createdAt between :from and :to
         group by r.negative
    """)
    List<Object[]> sentimentCountsIn(@Param("from") Date from,
                                     @Param("to") Date to);

    // (임시) 기간 내 리뷰 수 기준 상품 TOP (상품ID, 리뷰수) — 가입 Top 대체용
    @Query("""
        select r.product.productId, count(r.id)
          from ProductReview r
         where r.createdAt between :from and :to
         group by r.product.productId
         order by count(r.id) desc
    """)
    List<Object[]> topProductsByReviewIn(@Param("from") Date from,
                                         @Param("to") Date to);
}