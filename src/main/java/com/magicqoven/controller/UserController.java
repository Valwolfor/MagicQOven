package com.magicqoven.controller;

import com.magicqoven.controller.request.SuperUserPromotionRequest;
import com.magicqoven.entity.SuperUser;
import com.magicqoven.entity.User;
import com.magicqoven.entity.util.UserRole;
import com.magicqoven.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/{id}/promote-to-superuser")
    public ResponseEntity<SuperUser> promoteToSuperUser(
            @PathVariable("id") Long userId,
            @RequestBody SuperUserPromotionRequest request) {
        User user = userService.getUserById(userId);

        if (user != null) {
            SuperUser newSuperUser = userService.promoteToSuperUser(user, request.getPassword(), request.getUsername());
            return ResponseEntity.ok(newSuperUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(
            @RequestParam("email") String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<User>> getUsersByRole(
            @RequestParam(name = "role") String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        List<User> users = userService.getUsersByRole(userRole);
        return ResponseEntity.ok(users);
    }
}
