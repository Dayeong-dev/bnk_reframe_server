package com.example.reframe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	List<User> findByUsertype(String string);

	List<User> findByRole(String role);

}
