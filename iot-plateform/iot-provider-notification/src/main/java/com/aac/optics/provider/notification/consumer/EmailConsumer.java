package com.aac.optics.provider.notification.consumer;


import com.aac.optics.provider.notification.config.BusConfig;
import com.aac.optics.provider.notification.entity.po.EmailSend;
import com.aac.optics.provider.notification.service.MailService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @Author : Yan Shangqi
 * @CreateTime : 2021/5/15
 * @Description :
 **/
@Component
@RabbitListener(queues = BusConfig.EMAIL_ROUTING)
public class EmailConsumer {
    @Autowired
    private MailService mailService;

    @RabbitHandler
    public void process(Map Message) {
        JSONObject mailJson = JSONObject.parseObject(Message.get("messageData").toString());
        EmailSend emailSend = JSON.toJavaObject(mailJson, EmailSend.class);
        try {
            mailService.send(emailSend);
        } catch (MessagingException err) {
            err.printStackTrace();
        }
        System.out.println(BusConfig.EMAIL_ROUTING + "消费者收到消息  : " + Message.toString());
    }
}