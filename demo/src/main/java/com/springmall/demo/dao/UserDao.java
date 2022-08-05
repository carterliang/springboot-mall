package com.springmall.demo.dao;

import com.springmall.demo.dto.UserRegisterRequest;
import com.springmall.demo.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);

}
