package com.example.reframe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
