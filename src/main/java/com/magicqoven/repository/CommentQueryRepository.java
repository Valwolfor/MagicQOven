package com.magicqoven.repository;

import com.magicqoven.entity.CommentQuery;
import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentQueryRepository extends JpaRepository<CommentQuery, Long> {
    List<CommentQuery> findBySavedQuery(SavedQueryBuilt savedQuery);

    List<CommentQuery> findByUser(User user);
}