package com.magicqoven.controller;

import com.magicqoven.entity.SuperUser;
import com.magicqoven.entity.User;
import com.magicqoven.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class SessionController {

    private final UserServiceImpl userService;

    @Autowired
    public SessionController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/check-session")
    public ResponseEntity<User> checkSession(@RequestBody String email) {
        User user = userService.findByEmail(email);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<User> createSession(@RequestBody User user) {
        System.out.println("Entró");
        User loggedInUser = userService.saveUser(user);
        System.out.println(loggedInUser);
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login-admin")
    public ResponseEntity<User> createSessionAdmin(@RequestBody SuperUser user) {
        SuperUser loggedInAdmin = userService.saveSuperUser(user);
        if (loggedInAdmin != null) {
            return ResponseEntity.ok(loggedInAdmin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
