package com.treasuremount.petshop.Service;


import com.treasuremount.petshop.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    EmailService(JavaMailSender mailSender){
        this.emailSender=mailSender;
    }

    @Autowired
    private UserRepo userRepo;

    // Order confirmation email to user
    public void sendEmailNotification(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

}
