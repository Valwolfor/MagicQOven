package com.magicqoven.repository;

import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findUsersByRole(@Param("role") UserRole role);
}