package com.magicqoven.repository;

import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findUsersByRole(UserRole role);
}