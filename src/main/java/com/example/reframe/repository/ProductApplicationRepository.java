package com.example.reframe.repository;
import java.time.LocalDateTime;
import java.util.List;

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
}
