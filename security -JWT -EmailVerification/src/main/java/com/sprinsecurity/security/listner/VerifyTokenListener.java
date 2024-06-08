package com.sprinsecurity.security.listner;

import com.sprinsecurity.security.entity.User;
import com.sprinsecurity.security.events.VerifyTokenEvent;
import com.sprinsecurity.security.service.MailService;
import com.sprinsecurity.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class VerifyTokenListener implements ApplicationListener<VerifyTokenEvent> {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;
    @Override
    public void onApplicationEvent(VerifyTokenEvent event) {
        User user=event.getUser();
        String token= UUID.randomUUID().toString();
        userService.saveVerificationToken(user,token);
        String url=event.getUrl().toString()+"/user/verifyRegistration?token="+token;
        log.info("click here to verify account   "+ url);
        mailService.sendMail(user.getEmail(),url);
    }

}
