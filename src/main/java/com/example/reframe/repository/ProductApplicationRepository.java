package com.example.reframe.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.reframe.entity.ProductApplication;

@Repository
public interface ProductApplicationRepository extends JpaRepository<ProductApplication, Long> {

    @EntityGraph(attributePaths = { "user", "product", "productAccount", "fromAccount", "maturityAccount" })
    @Query("select a from ProductApplication a where a.product.productId = :productId")
    List<ProductApplication> findAllByProductIdWithJoins(@Param("productId") Long productId);

    long countByProduct_ProductId(Long productId);
    long countByProduct_ProductIdAndStatus(Long productId, ProductApplication.ApplicationStatus status);
    long countByProduct_ProductIdAndStartAtAfter(Long productId, LocalDateTime after);
    
    @Query("""
            select a
            from ProductApplication a
              join fetch a.user u
              join fetch a.product p
              join fetch a.productAccount pa
              left join fetch a.fromAccount fa
            where p.id = :productId
            order by a.startAt desc
            """)
        List<ProductApplication> findByProductIdWithJoins(@Param("productId") Long productId);
    
    @Query("""
            select
              count(pa.id) as total,
              sum(case when upper(pa.status) in ('IN_PROGRESS','STARTED') then 1 else 0 end) as inProgress,
              sum(case when upper(pa.status) in ('DONE','COMPLETED','COMPLETE','CLOSED') then 1 else 0 end) as completed,
              sum(case when upper(pa.status) in ('CANCEL','CANCELED','CANCELLED','CANCLED') then 1 else 0 end) as canceled
            from ProductApplication pa
            """)
        ApplicationSummaryProjection getSummaryCounts();
    
    
    @Query("""
    		select new map(
    		  u.name as userName,
    		  pa.startAt as startAt,
    		  case
    		    when upper(pa.status) in ('IN_PROGRESS','STARTED') then 'IN_PROGRESS'
    		    when upper(pa.status) in ('DONE','COMPLETED','COMPLETE','CLOSED') then 'COMPLETED'
    		    when upper(pa.status) in ('CANCEL','CANCELED','CANCELLED','CANCLED') then 'CANCELED'
    		    else 'UNKNOWN'
    		  end as status,
    		  prodAcc.accountNumber as productAccountNumber
    		)
    		from ProductApplication pa
    		join pa.user u
    		left join pa.productAccount prodAcc
    		where pa.product.id = :productId
    		order by pa.startAt desc
    		""")
        List<Map<String, Object>> findByProductIdForAdmin(@Param("productId")Long productId);

	ProductApplication findByProductAccount_Id(Long accountId);

	ProductApplication findByProductAccount_IdAndUser_Id(Long accountId, Long uid);

	List<ProductApplication> findByUser_IdOrderByStartAtDesc(Long uid);
}
