package com.magicqoven.service.impl;

import com.magicqoven.entity.CommentQuery;
import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import com.magicqoven.repository.CommentQueryRepository;
import com.magicqoven.service.inter.CommentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;

    @Autowired
    public CommentQueryServiceImpl(CommentQueryRepository commentQueryRepository) {
        this.commentQueryRepository = commentQueryRepository;
    }

    @Override
    public CommentQuery saveComment(CommentQuery comment) {
        return commentQueryRepository.save(comment);
    }

    @Override
    public List<CommentQuery> getCommentsBySavedQuery(SavedQueryBuilt savedQuery) {
        return commentQueryRepository.findBySavedQuery(savedQuery);
    }

    @Override
    public List<CommentQuery> getCommentsByUser(User user) {
        return commentQueryRepository.findByUser(user);
    }
}

