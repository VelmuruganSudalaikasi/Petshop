package com.treasuremount.petshop;


import com.treasuremount.petshop.Service.EmailService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailTestSerivce {

    @Autowired
    EmailService mailSender;


    @Test
    void TestingMailForMe(){

        String html= """
                "Dear Dr. " + vetName + ",\\n\\n" +
                               "Thank you for registering as a veterinarian at "+application_name+". We are excited to have you as part of our team.\\n\\n" +
                               "You can now start offering consultations and check-ups for pets. If you need any assistance, feel free to reach out to our support team.\\n\\n" +
                               "Best regards,\\nThe "+application_name+" Team\\n\\n" +
                               "---\\nIf you didn't register an account with us, please ignore this email.";
                """;



        mailSender.sendEmailNotification(
                "velmuruganpsy@gmail.com",
                "Testing_Mail",
                        html
        );

    }

}