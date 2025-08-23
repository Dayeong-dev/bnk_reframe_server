package com.example.reframe.repository.deposit;

import com.example.reframe.entity.deposit.ProductViewLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductViewLogRepository extends JpaRepository<ProductViewLog, Long> {

  // 월간 조회수 TOP N
  @Query("""
    select l.product.productId, count(l.id)
      from ProductViewLog l
     where l.viewedAt >= :start and l.viewedAt < :endExcl
     group by l.product.productId
     order by count(l.id) desc
  """)
  List<Object[]> topViewedBetween(@Param("start") LocalDateTime start,
                                  @Param("endExcl") LocalDateTime endExcl,
                                  Pageable pageable);

  // (옵션) 월말 시점까지 누적 TOP N
  @Query("""
    select l.product.productId, count(l.id)
      from ProductViewLog l
     where l.viewedAt < :endExcl
     group by l.product.productId
     order by count(l.id) desc
  """)
  List<Object[]> topViewedUntil(@Param("endExcl") LocalDateTime endExcl,
                                Pageable pageable);
}