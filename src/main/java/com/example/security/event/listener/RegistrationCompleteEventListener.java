package com.example.security.event.listener;

import com.example.security.appuser.AppUser;
import com.example.security.appuser.AppUserService;
import com.example.security.event.RegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
//import java.util.logging.Logger;

//@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent>
{
private final AppUserService appUserService;

private final JavaMailSender mailSender;

private AppUser theAppUser;

private Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
         //  1  Get the newly registered user
         theAppUser = event.getAppUser();
         //  2  Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
          // 3  Save the verification token
        appUserService.saveAppUserVerification(theAppUser, verificationToken);
          // 4  Build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
          // 5  Send the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration: {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Expense tracker";
        String mailContent = "<p> Hi, "+ theAppUser.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("godseedchukwuemeka@gmail.com", senderName);
        messageHelper.setTo(theAppUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
