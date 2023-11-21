package com.magicqoven.service.inter;

import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;

import java.util.List;

public interface UserService {

    User getUserById(Long id);
    User findByEmail(String email);
    List<User> getUsers();
    List<User> getUsersByRole(UserRole role);
    User saveUser(User user);
    User setUserAdmin(User user, UserRole role);
    void deleteUser(User user);
}
