package com.magicqoven.service.inter;

import com.magicqoven.entity.CommentQuery;
import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;

import java.util.List;

public interface CommentQueryService {
    CommentQuery saveComment(CommentQuery comment);
    List<CommentQuery> getCommentsBySavedQuery(SavedQueryBuilt savedQuery);
    List<CommentQuery> getCommentsByUser(User user);
}
