package com.magicqoven.service.impl;

import com.magicqoven.entity.SuperUser;
import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import com.magicqoven.repository.UserRepository;
import com.magicqoven.service.inter.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public SuperUser saveSuperUser(SuperUser superUser) {
        return userRepository.save(superUser);
    }

    @Override
    public SuperUser promoteToSuperUser(User user, String password, String username) {

        SuperUser superUser = new SuperUser();
        superUser.setEmail(user.getEmail());
        superUser.setPassword(password);
        superUser.setUsername(username);
        superUser.setRole(UserRole.ADMIN);

        return userRepository.save(superUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findUsersByRole(role);
    }

}