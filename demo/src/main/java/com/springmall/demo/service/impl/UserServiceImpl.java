package com.springmall.demo.service.impl;

import com.springmall.demo.dao.UserDao;
import com.springmall.demo.dto.UserRegisterRequest;
import com.springmall.demo.model.User;
import com.springmall.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){

        return userDao.createUser(userRegisterRequest);
    }

}
