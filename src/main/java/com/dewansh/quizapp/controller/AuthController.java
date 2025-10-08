package com.dewansh.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dewansh.quizapp.service.UserService;


@RestController
@RequestMapping("auth")
public class AuthController {

    // ok we will now make the login endpoint here and then we will Assign the JWT token for auth purposes
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password){
        return userService.handleLogin(username, password);
    }

}
