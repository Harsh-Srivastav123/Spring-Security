package com.sprinsecurity.security.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sprinsecurity.security.constants.ApplicationConstants;
import com.sprinsecurity.security.entity.User;
import com.sprinsecurity.security.events.VerifyTokenEvent;
import com.sprinsecurity.security.exception.BadRequestException;
import com.sprinsecurity.security.security.JwtServices;
import com.sprinsecurity.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
@Slf4j
public class MainController {

    @Autowired
    Gson gson;

    @Autowired
    UserService userService;

    @Autowired
    JwtServices jwtServices;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping("/createUser")
    public String createUser(@RequestBody User user, HttpServletRequest request){
        for(String str:user.getRoles()){
            if(!ApplicationConstants.ROLE_LIST.contains(str)){
                return "undefined role is present remove it !!";
            }
        }
        userService.createUser(user);
        publisher.publishEvent(new VerifyTokenEvent(user,generateUrl(request)));
        return "user created successfully";
    }

    @GetMapping("verifyRegistration")
    public ResponseEntity<Object> verifyRegistration(@RequestParam("token") String token){
        int status=userService.validateToken(token);
        if(status==0){
            return new ResponseEntity<>("enter valid token", HttpStatus.BAD_REQUEST);
        }
        if(status==1){
            return new ResponseEntity<>("token expired",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("token  verified Successfully ",HttpStatus.OK);
    }

    private String generateUrl(HttpServletRequest request) {

        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Object getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/auth")
    public Object login(@RequestBody String response){
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String userName=jsonObject.get("userName").getAsString();
        String password=jsonObject.get("password").getAsString();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName ,password));
        if(authentication.isAuthenticated()){
            return jwtServices.generateToken(userName);
        }
        else {
            log.info("error reach controller  !!");
            throw new BadRequestException("Invalid credentials!!");
        }


    }

    @GetMapping("/error")
    public void error(){
        throw new BadRequestException("Invalid credentials!!");
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


    @GetMapping("/findById")
    public Object findById(@RequestParam(value = "id",required = true) String id){
        return userService.findById(id);
    }
}
