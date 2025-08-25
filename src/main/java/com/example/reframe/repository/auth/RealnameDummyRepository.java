package com.example.reframe.repository.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.auth.RealnameDummy;

public interface RealnameDummyRepository extends JpaRepository<RealnameDummy, Long> {

	Optional<RealnameDummy> findByNameAndRrnFrontAndCarrierAndPhoneNumber(String name, String rrnFront,
			String carrier, String phone);

}
