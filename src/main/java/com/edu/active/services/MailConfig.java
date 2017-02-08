package com.edu.active.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("noreply.active@gmail.com");
        javaMailSender.setPassword("199519951995");
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.debug", "false");
        return properties;
    }


//    @Bean
//    public VelocityEngineFactoryBean velocityEngine() {
//        VelocityEngineFactoryBean velocityEngine =
//                new VelocityEngineFactoryBean();
//        Properties props = new Properties();
//        props.setProperty("resource.loader", "class");
//        props.setProperty("class.resource.loader.class",
//                ClasspathResourceLoader.class.getName());
//        velocityEngine.setVelocityProperties(props);
//        return velocityEngine;
//    }
}