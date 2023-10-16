package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    User findByUsername(String username);

	Optional<User> findById(Integer id);
}