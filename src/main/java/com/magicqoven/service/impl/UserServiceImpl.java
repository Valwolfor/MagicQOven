package com.magicqoven.service.impl;

import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import com.magicqoven.repository.UserRepository;
import com.magicqoven.service.inter.UserService;
import jakarta.transaction.Transactional;
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
    public User setUserAdmin(User user, UserRole role) {
        User old = userRepository.getReferenceById(user.getId());
        if (role.compareTo(UserRole.ADMIN) == 0) {
            old.setRole(role);
            return userRepository.save(old);
        }else {
            throw new IllegalStateException("Userrole is not ADMIN " + user);
        }
    }

    @Override
    public void deleteUser(User user) {

        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
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