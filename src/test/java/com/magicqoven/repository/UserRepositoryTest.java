package com.magicqoven.repository;

import com.magicqoven.entity.SuperUser;
import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void testSaveUser() {

        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("normalUser");
        user.setRole(UserRole.ANALYST);

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser.getId());
        Assertions.assertEquals("user@example.com", savedUser.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveSuperUser() {

        SuperUser superUser = new SuperUser();
        superUser.setEmail("superuser@example.com");
        superUser.setUsername("superuser");
        superUser.setSuperUsername("superUsername");
        superUser.setSuperUserRole(UserRole.ADMIN);
        superUser.setPassword("superpassword");

        SuperUser savedSuperUser = userRepository.save(superUser);

        Assertions.assertNotNull(savedSuperUser.getId());
        Assertions.assertEquals("superuser@example.com", savedSuperUser.getEmail());
        Assertions.assertEquals("superUsername", savedSuperUser.getSuperUsername());
        Assertions.assertEquals("superpassword", savedSuperUser.getPassword());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByEmail() {

        User user = new User();
        user.setEmail("findme@example.com");
        user.setUsername("findme");
        user.setRole(UserRole.ANALYST);
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("findme@example.com");

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("findme@example.com", foundUser.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUsersByRole() {

        User user = new User();
        user.setEmail("roleuser@example.com");
        user.setUsername("roleuser");
        user.setRole(UserRole.ANALYST);
        userRepository.save(user);

        List<User> usersWithRole = userRepository.findUsersByRole(UserRole.ANALYST);

        Assertions.assertFalse(usersWithRole.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setEmail("update@example.com");
        user.setUsername("updateUser");
        user.setRole(UserRole.ANALYST);
        User savedUser = userRepository.save(user);

        // Act
        savedUser.setUsername("updatedUsername");
        User updatedUser = userRepository.save(savedUser);

        // Assert
        Assertions.assertEquals("updatedUsername", updatedUser.getUsername());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setEmail("delete@example.com");
        user.setUsername("deleteUser");
        user.setRole(UserRole.ANALYST);
        User savedUser = userRepository.save(user);

        // Act
        userRepository.delete(savedUser);

        // Assert
        Assertions.assertFalse(userRepository.existsById(savedUser.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateSuperUser() {

        SuperUser superUser = new SuperUser();
        superUser.setEmail("superuser@example.com");
        superUser.setUsername("superuser");
        superUser.setSuperUsername("superUsername");
        superUser.setSuperUserRole(UserRole.ADMIN);
        superUser.setPassword("superpassword");
        SuperUser savedSuperUser = userRepository.save(superUser);

        savedSuperUser.setSuperUsername("updatedSuperUsername");
        SuperUser updatedSuperUser = userRepository.save(savedSuperUser);

        Assertions.assertEquals("updatedSuperUsername", updatedSuperUser.getSuperUsername());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteSuperUser() {

        SuperUser superUser = new SuperUser();
        superUser.setEmail("delete@example.com");
        superUser.setUsername("delete");
        superUser.setSuperUsername("deleteSuperUsername");
        superUser.setSuperUserRole(UserRole.ADMIN);
        superUser.setPassword("deletepassword");
        SuperUser savedSuperUser = userRepository.save(superUser);

        userRepository.delete(savedSuperUser);

        Assertions.assertFalse(userRepository.existsById(savedSuperUser.getId()));
    }
}
