package com.sprinsecurity.security.service;

import com.sprinsecurity.security.dao.UserDao;
import com.sprinsecurity.security.dao.VerifyTokenDao;
import com.sprinsecurity.security.entity.User;
import com.sprinsecurity.security.entity.VerifyToken;
import com.sprinsecurity.security.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    VerifyTokenDao verifyTokenDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userDao.save(user);
    }

    public Object getAllUser() {
        return userDao.findAll();
    }

    public Object getUser1() {
        return userDao.findUsersByRole("USER-1");
    }

    public Object getUser2() {
        return userDao.findUsersByRole("USER-2");
    }

    public Object deleteUser(String userId) {
        User user=userDao.findById(userId).get();
        userDao.delete(user);
        return  user;
    }

    public Object findById(String id) {
        if(!userDao.existsById(id)){
            throw new BadRequestException("Unable to find user");
        }
        return userDao.findById(id).get();
    }

    public void saveVerificationToken(User user, String token) {
        verifyTokenDao.save(new VerifyToken(token,user));
    }

    public int validateToken(String token) {

        VerifyToken verifyToken = verifyTokenDao.findByToken(token);
        if (verifyToken == null) {
            return 1;
        }



        User user = verifyToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verifyToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verifyTokenDao.delete(verifyToken);
            userDao.delete(verifyToken.getUser());
            return 2;
        }
        user.setActive(true);
        userDao.save(user);
        return 3;
    }
}
