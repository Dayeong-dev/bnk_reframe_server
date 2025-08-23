package com.example.reframe.entity.deposit;


import com.example.reframe.entity.DepositProduct;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_VIEW_LOG")
public class ProductViewLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_ID", nullable = false)
  private DepositProduct product;

  @Column(name = "VIEWED_AT", nullable = false)
  private LocalDateTime viewedAt;

  @Column(name = "USER_ID")
  private Long userId;

  // getters/setters
  public Long getId(){ return id; }
  public DepositProduct getProduct(){ return product; }
  public void setProduct(DepositProduct p){ this.product = p; }
  public LocalDateTime getViewedAt(){ return viewedAt; }
  public void setViewedAt(LocalDateTime t){ this.viewedAt = t; }
  public Long getUserId(){ return userId; }
  public void setUserId(Long u){ this.userId = u; }
}