package com.example.backend.services;

import com.example.backend.entities.Profile;

import java.time.LocalDate;


public interface EmailService {
    void sendEmail(String to, String subject, String message);
    void sendReminderEmailsCreateProfile(Profile profile, LocalDate eventDate);
    void sendReminderEmails(Profile profile);
    void sendReminderEmailsNotCreateProfile(Profile profile, LocalDate eventDate);
    void sendReminderEmailsReverseProfile(Profile item, LocalDate eventDate);
    void sendReminderEmailsNotApprove(String email, LocalDate evenDate);
    void sendReminderEmailsApprove(String email, LocalDate evenDate);
}
