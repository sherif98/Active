package com.edu.active.services;

import com.edu.active.controllers.dto.User;

public interface EmailService {

    public void sendEmail(User user);
}
