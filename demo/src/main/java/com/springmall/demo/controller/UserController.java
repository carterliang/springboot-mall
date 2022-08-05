package com.springmall.demo.controller;

import com.springmall.demo.dto.UserRegisterRequest;
import com.springmall.demo.model.User;
import com.springmall.demo.service.UserService;
import com.springmall.demo.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@Validated
@RestController
public class UserController {
    @Autowired
    private UserService userService ;
    private final static Logger log   = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest)
    {

        Integer userId = userService.register(userRegisterRequest);
        User user = userService.getUserById(userId);
        log.warn("User id{}"+user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
