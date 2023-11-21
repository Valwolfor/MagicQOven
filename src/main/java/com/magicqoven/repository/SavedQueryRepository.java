package com.magicqoven.repository;

import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedQueryRepository extends JpaRepository<SavedQueryBuilt, Long> {
    List<SavedQueryBuilt> findByUser(User user);

    List<SavedQueryBuilt> findByName(String name);
}