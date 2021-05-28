package com.aac.optics.provider.notification.service;

import com.aac.optics.provider.notification.entity.po.EmailSend;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {
    boolean send(EmailSend emailSend) throws MessagingException;
}
