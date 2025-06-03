package com.music.application.be.modules.forget_password;

import com.music.application.be.modules.forget_password.dto.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public void sendSimpleEmail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
         message.setTo(mailBody.to());
         message.setFrom("lem247358@gmail.com");
         message.setSubject(mailBody.subject());
         message.setText(mailBody.text());

         mailSender.send(message);
    }

}
