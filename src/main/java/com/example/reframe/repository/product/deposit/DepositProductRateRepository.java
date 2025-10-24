package com.example.reframe.repository.product.deposit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.product.deposit.DepositProduct;
import com.example.reframe.entity.product.deposit.DepositProductRate;

public interface DepositProductRateRepository extends JpaRepository<DepositProductRate, Long> {

	List<DepositProductRate> findAllByProduct_ProductIdOrderByFromMonthAsc(Long productId);
	
	@Query("""
      select r from DepositProductRate r
      where r.product.productId = :productId
        and r.fromMonth <= :months
        and (r.toMonth is null or r.toMonth >= :months)
      order by r.fromMonth desc""")
    Optional<DepositProductRate> findBestTierForMonths(@Param("productId") Long productId,
                                                       @Param("months") Integer months);

	Optional<DepositProductRate> findTopByProduct_ProductIdAndToMonthIsNullOrderByFromMonthDesc(Long productId);

}
