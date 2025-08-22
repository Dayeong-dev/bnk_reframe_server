package com.example.reframe.repository.enroll;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.enroll.ProductApplicationInput;

public interface ProductApplicationInputRepository extends JpaRepository<ProductApplicationInput, Long> {

	Optional<ProductApplicationInput> findByApplication_Id(Long id);

}
