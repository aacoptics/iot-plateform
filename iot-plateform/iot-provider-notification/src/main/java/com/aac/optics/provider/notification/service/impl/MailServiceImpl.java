package com.aac.optics.provider.notification.service.impl;


import com.aac.optics.provider.notification.entity.po.EmailSend;
import com.aac.optics.provider.notification.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    //@Value(value = "${spring.profiles}")
    private String from = "s-optics@aacoptics.com";

    @Override
    public boolean send(EmailSend emailSend) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(emailSend.getTo().toArray(new String[emailSend.getTo().size()]));
        helper.setSubject(emailSend.getSubject());
        helper.setText(emailSend.getEmailContent(), true);
        mailSender.send(message);
        return true;
    }
}