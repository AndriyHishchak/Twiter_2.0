package com.Spring.SpringLetsCode.service;

import com.Spring.SpringLetsCode.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;
    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String massage) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailTo);
        mail.setFrom(username);
        mail.setSubject(subject);
        mail.setText(massage);
        System.out.println("Send 200_OK");
        javaMailSender.send(mail);

    }
}
