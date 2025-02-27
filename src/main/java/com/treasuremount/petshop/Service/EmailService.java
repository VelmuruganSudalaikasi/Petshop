package com.treasuremount.petshop.Service;


import com.treasuremount.petshop.Repository.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String emailBody=customModification(body);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(emailBody, true); // Enable HTML content

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle error properly in production
        }
    }

    public String customModification(String body){
       String template="<p>"+ body +"</p>";
       String footerTemplate = """
               <p style="font-size: 12px; color: #888;">
                       <strong>Note:</strong> This is an automated email message. Replies to this email will not be received. \s
                       If you need to contact the TreasureMount team, please visit our website for more details: \s
                       <a href="https://www.treasuremount.support" style="color: #007bff; text-decoration: none;">TreasureMount Support</a>.
                   </p>
               """;

       return template+footerTemplate;
    }

}
