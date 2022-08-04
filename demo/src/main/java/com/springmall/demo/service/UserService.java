package com.springmall.demo.service;

import com.springmall.demo.dto.UserRegisterRequest;
import com.springmall.demo.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
}
