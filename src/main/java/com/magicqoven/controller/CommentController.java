package com.magicqoven.controller;

import com.magicqoven.entity.CommentQuery;
import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import com.magicqoven.service.inter.CommentQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class CommentController {

    private final CommentQueryService commentQueryService;

    public CommentController(CommentQueryService commentQueryService) {
        this.commentQueryService = commentQueryService;
    }

    @PostMapping("/save")
    public ResponseEntity<CommentQuery> saveComment(@RequestBody CommentQuery comment) {
        CommentQuery savedComment = commentQueryService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping("/by-saved-query")
    public ResponseEntity<List<CommentQuery>> getCommentsBySavedQuery(
            @RequestParam("savedQueryId") Long savedQueryId) {
        SavedQueryBuilt savedQuery = new SavedQueryBuilt();
        savedQuery.setId(savedQueryId);
        List<CommentQuery> comments = commentQueryService.getCommentsBySavedQuery(savedQuery);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<CommentQuery>> getCommentsByUser(
            @RequestParam("userId") Long userId) {
        User user = new User();
        user.setId(userId);
        List<CommentQuery> comments = commentQueryService.getCommentsByUser(user);
        return ResponseEntity.ok(comments);
    }
}
