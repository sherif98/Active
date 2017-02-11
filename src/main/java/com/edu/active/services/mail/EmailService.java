package com.edu.active.services.mail;

import com.edu.active.services.storage.model.User;

public interface EmailService {

    public void sendEmail(User user);
}
