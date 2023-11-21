package com.magicqoven.repository;

import com.magicqoven.entity.CommentQuery;
import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentQueryRepository commentQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedQueryRepository savedQueryRepository;

    @Test
    @Transactional
    @Rollback
    public void testSaveCommentQuery() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        userRepository.save(user);

        SavedQueryBuilt savedQuery = new SavedQueryBuilt();
        savedQuery.setName("Test Query");
        savedQuery.setUser(user);
        savedQueryRepository.save(savedQuery);

        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setText("This is a test comment");
        commentQuery.setCreationDateTime(LocalDateTime.now());
        commentQuery.setUser(user);
        commentQuery.setSavedQuery(savedQuery);

        // Act
        CommentQuery savedCommentQuery = commentQueryRepository.save(commentQuery);

        // Assert
        Assertions.assertNotNull(savedCommentQuery.getId());
        Assertions.assertEquals("This is a test comment", savedCommentQuery.getText());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        userRepository.save(user);

        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setText("Comment by user");
        commentQuery.setCreationDateTime(LocalDateTime.now());
        commentQuery.setUser(user);
        commentQueryRepository.save(commentQuery);

        // Act
        User foundUser = userRepository.findByEmail("test@example.com");
        Assertions.assertNotNull(foundUser);

        // Get comments by the user
        List<CommentQuery> commentsByUser = commentQueryRepository.findByUser(foundUser);

        // Assert
        Assertions.assertFalse(commentsByUser.isEmpty());
        Assertions.assertEquals("Comment by user", commentsByUser.get(0).getText());
    }
    @Test
    @Transactional
    @Rollback
    public void testDeleteCommentQuery() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        userRepository.save(user);

        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setText("Comment to delete");
        commentQuery.setCreationDateTime(LocalDateTime.now());
        commentQuery.setUser(user);
        CommentQuery savedCommentQuery = commentQueryRepository.save(commentQuery);

        // Act
        commentQueryRepository.delete(savedCommentQuery);

        // Assert
        Assertions.assertNull(commentQueryRepository.findById(savedCommentQuery.getId()).orElse(null));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateCommentQuery() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        userRepository.save(user);

        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setText("Initial comment text");
        commentQuery.setCreationDateTime(LocalDateTime.now());
        commentQuery.setUser(user);
        CommentQuery savedCommentQuery = commentQueryRepository.save(commentQuery);

        // Act
        savedCommentQuery.setText("Updated comment text");
        CommentQuery updatedCommentQuery = commentQueryRepository.save(savedCommentQuery);

        // Assert
        Assertions.assertEquals("Updated comment text", updatedCommentQuery.getText());
    }

}
