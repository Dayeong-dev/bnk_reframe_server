package com.example.reframe.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
   
   @Column(name = "regdate", updatable = false)
   @CreatedDate
   private LocalDateTime regdate;
   
   @Column(name = "moddate")
   @LastModifiedDate
   private LocalDateTime modDate;
   
   
}
