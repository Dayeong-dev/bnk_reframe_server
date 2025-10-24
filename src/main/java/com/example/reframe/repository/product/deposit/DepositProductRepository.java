package com.example.reframe.repository.product.deposit;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.reframe.dto.admin.AdminProductListItemDto;
import com.example.reframe.entity.product.deposit.DepositProduct;

@Repository
public interface DepositProductRepository extends JpaRepository<DepositProduct, Long> {

    // ✅ 기본 제공: findById, findAll, save, delete 등

    // 🔍 목적별 상품 조회
    List<DepositProduct> findByPurpose(String purpose);

    // 🔍 카테고리별 상품 조회
    List<DepositProduct> findByCategory(String category);

    // 🔍 상태별 조회
    List<DepositProduct> findByStatus(String status);

    // 🔍 이름 LIKE 검색
    List<DepositProduct> findByNameContaining(String keyword);

    // 🔍 목적 + 상태별 조회
    List<DepositProduct> findByPurposeAndStatus(String purpose, String status);

    // 🔍 카테고리 + 상태별 조회
    List<DepositProduct> findByCategoryAndStatus(String category, String status);

    // ✅✅ ✅✅ ✅✅
    // 📌 📌 페이지네이션 + 정렬 + 검색용 추가 메서드
    // 상태 + 이름 포함 OR 상태 + 요약 포함
    Page<DepositProduct> findByStatusAndNameContainingOrStatusAndSummaryContaining(
            String status1, String name,
            String status2, String summary,
            Pageable pageable
    );

    // 상태 + 카테고리
    Page<DepositProduct> findByStatusAndCategory(
            String status,
            String category,
            Pageable pageable
    );

    // 상태만
    Page<DepositProduct> findByStatus(
            String status,
            Pageable pageable
    );


	List<DepositProduct> findTop10ByOrderByViewCountDesc();

    List<DepositProduct> findByPurposeInAndStatus(List<String> purposes, String status);

    @Query(value = "SELECT * FROM (SELECT * FROM DEPOSIT_PRODUCT WHERE view_count IS NOT NULL AND status = 'S' ORDER BY view_count DESC) WHERE ROWNUM <= 5", nativeQuery = true)
	List<DepositProduct> findTopFiveByViewCount();
    
    @Query("SELECT d.name FROM DepositProduct d")
    List<String> findAllNames();
    
    @Query("select p from DepositProduct p where p.status = 'S'")
    List<DepositProduct> findActive();
    
    @Query("""
    		select new com.example.reframe.dto.admin.AdminProductListItemDto(
    		  dp.id, dp.name, dp.purpose, dp.category, dp.period,
    		  count(pa.id),
    		  sum(case when upper(pa.status) in ('IN_PROGRESS','STARTED') then 1 else 0 end),                         
    		  sum(case when upper(pa.status) in ('DONE','COMPLETED','COMPLETE','CLOSED') then 1 else 0 end),          
    		  sum(case when upper(pa.status) in ('CANCEL','CANCELED','CANCELLED','CANCLED') then 1 else 0 end)        
    		)
    		from DepositProduct dp
    		left join ProductApplication pa on pa.product.id = dp.id
    		where dp.status = 'S'
    		group by dp.id, dp.name, dp.purpose, dp.category, dp.period
    		order by dp.id asc
    		""")
        List<AdminProductListItemDto> findProductsWithCounts();
   
    // 누적 조회수 TOP N (viewCount 컬럼 기반)
    @Query("""
        select p.productId, coalesce(p.viewCount, 0)
          from DepositProduct p
         order by coalesce(p.viewCount, 0) desc
    """)
    List<Object[]> topViewedAll();

    // 특정 productId -> name
    @Query("""
        select p.name from DepositProduct p
         where p.productId = :pid
    """)
    String findNameById(@Param("pid") Long productId);
}
