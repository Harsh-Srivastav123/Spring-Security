package com.sprinsecurity.security.controller;

import com.sprinsecurity.security.constants.ApplicationConstants;
import com.sprinsecurity.security.entity.User;
import com.sprinsecurity.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController

public class MainController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public String createUser(@RequestBody User user){
        for(String str:user.getRoles()){
            if(!ApplicationConstants.ROLE_LIST.contains(str)){
                return "undefined role is present remove it !!";
            }
        }

        userService.createUser(user);
        return "user created successfully";
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Object getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getUser1")
    public Object getAllUser1(){
        return userService.getUser1();
    }
    @GetMapping("/getUser2")
    public Object getAllUser2(){
        return userService.getUser2();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public Object deleteUser(@PathVariable String userId){
        return userService.deleteUser(userId);
    }
}
