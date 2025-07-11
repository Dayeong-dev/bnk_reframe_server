package com.example.reframe.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "faq_board")
public class Faq extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer faqId;
    
    private String category;

    private String question;
    
    private String answer;
    
    private String status;

}