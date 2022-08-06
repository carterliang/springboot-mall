package com.springmall.demo.service.impl;

import com.springmall.demo.dao.UserDao;
import com.springmall.demo.dao.impl.UserDaoImpl;
import com.springmall.demo.dto.UserLoginRequest;
import com.springmall.demo.dto.UserRegisterRequest;
import com.springmall.demo.model.User;
import com.springmall.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;



@Component
public class UserServiceImpl implements UserService {

    private final static Logger log   = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;


    @Override
    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){
          //檢查註冊
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user !=null) {
            log.warn("該E{}已經被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //using MD5 to generate password hash table
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);


        //Create Account
        return userDao.createUser(userRegisterRequest);
    }

    public User login(UserLoginRequest userLoginRequest){
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        //check user if exist or not
        if (user == null) {
           log.warn("this email{} not yet register",userLoginRequest.getEmail());
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //using MD5 generate password hash value
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else {
            log.warn("email{}密碼步正確",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


    }
}
