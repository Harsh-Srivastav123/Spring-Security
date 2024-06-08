package com.sprinsecurity.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    @Value("$(SpringSecurity)")
    String from;
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String mail,String message){
        log.info("mail send to =  "+ mail);
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject("Verify Email to login to security app");
        simpleMailMessage.setText("verify your email  "+message);
        simpleMailMessage.setTo(mail);
        javaMailSender.send(simpleMailMessage);
    }
}
