package com.sprinsecurity.security.service;

import com.sprinsecurity.security.dao.UserDao;
import com.sprinsecurity.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
}
