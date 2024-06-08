package com.sprinsecurity.security.service;

import com.sprinsecurity.security.dao.UserDao;
import com.sprinsecurity.security.entity.User;
import com.sprinsecurity.security.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsInfoService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userDao.findByUserName(username);

        return user.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
