package com.magicqoven.service.inter;

import com.magicqoven.entity.SuperUser;
import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;

import java.util.List;

public interface UserService {

    User getUserById(Long id);
    User findByEmail(String email);
    List<User> getUsers();
    List<User> getUsersByRole(UserRole role);
    User saveUser(User user);
    SuperUser saveSuperUser(SuperUser superUser);
    SuperUser promoteToSuperUser(User user, String password, String username);
    void deleteUser(User user);
}
