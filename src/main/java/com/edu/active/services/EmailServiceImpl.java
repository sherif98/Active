package com.edu.active.services;

import com.edu.active.controllers.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

//    @Autowired
//    VelocityEngine velocityEngine;

//    public void sendEmail(User user) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("noreply.active@gmail.com");
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Registration");
//        mailMessage.setText("Hello " + user.getUserName() + "\n Your registration is successfull");
//        mailSender.send(mailMessage);
//    }

    @Override
    public void sendEmail(User user) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply.active@gmail.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Active " +
                    user.getUserName());
            helper.setText("<html><body><img src='cid:activeLogo'>" +
                    "<h4>" + "Hello, " + user.getUserName() + "</h4>" +
                    "<i>" + " Your registration is successful" + "</i>" +
                    "</body></html>", true);
            ClassPathResource image = new ClassPathResource("static/active.png");
            helper.addInline("activeLogo", image);
        } catch (MessagingException e) {
            log.error("failed to send email to user " + user);
            e.printStackTrace();
        }
        mailSender.send(message);
    }
}
