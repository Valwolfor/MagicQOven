package com.magicqoven.repository;

import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
public class SavedQueryRepositoryTest {

    @Autowired
    private SavedQueryRepository savedQueryRepository;

    @Autowired
    private UserRepository userRepository;
    @Test
    @Transactional
    @Rollback
    public void testSaveQuery() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("normalUser");
        user.setRole(UserRole.ANALYST);

        User savedUser = userRepository.save(user);

        SavedQueryBuilt query = new SavedQueryBuilt();
        query.setCreationDate(LocalDate.now());
        query.setName("Test Query");
        query.setDescription("Test Description");
        query.setQueryParameters("Test Parameters");
        query.setQuery("SELECT * FROM Test");

        // Set the user who saved the query
        query.setUser(savedUser);

        SavedQueryBuilt savedQuery = savedQueryRepository.save(query);

        Assertions.assertNotNull(savedQuery.getId());
        Assertions.assertEquals("Test Query", savedQuery.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByName() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("normalUser");
        user.setRole(UserRole.ANALYST);

        User savedUser = userRepository.save(user);

        SavedQueryBuilt query = new SavedQueryBuilt();
        query.setCreationDate(LocalDate.now());
        query.setName("Test Query");
        query.setDescription("Test Description");
        query.setQueryParameters("Test Parameters");
        query.setQuery("SELECT * FROM Test");

        query.setUser(savedUser);

        savedQueryRepository.save(query);

        SavedQueryBuilt foundQuery = savedQueryRepository.findByName("Test Query").get(0);

        Assertions.assertNotNull(foundQuery);
        Assertions.assertEquals("Test Query", foundQuery.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteQuery() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("normalUser");
        user.setRole(UserRole.ANALYST);

        User savedUser = userRepository.save(user);

        SavedQueryBuilt query = new SavedQueryBuilt();
        query.setCreationDate(LocalDate.now());
        query.setName("Test Query");
        query.setDescription("Test Description");
        query.setQueryParameters("Test Parameters");
        query.setQuery("SELECT * FROM Test");

        // Set the user who saved the query
        query.setUser(savedUser);

        SavedQueryBuilt savedQuery = savedQueryRepository.save(query);

        // Delete the query
        savedQueryRepository.delete(savedQuery);

        // Check if the query exists
        boolean exists = savedQueryRepository.existsById(savedQuery.getId());
        Assertions.assertFalse(exists);
    }

}
