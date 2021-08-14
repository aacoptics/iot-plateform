package com.aac.optics.provider.notification.controller;


import com.aac.optics.common.core.vo.Result;
import com.aac.optics.provider.notification.config.BusConfig;
import com.aac.optics.provider.notification.entity.form.EmailSendForm;
import com.aac.optics.provider.notification.entity.po.EmailSend;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/message")
@Api("notification")
@Slf4j
public class NotificationController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "发送邮件", notes = "发送邮件")
    @ApiImplicitParam(name = "emailSendForm", value = "邮件发送表单", required = true, dataType = "EmailSendForm")
    @PostMapping(value = "/sendEmail")
    public Result sendEmail(@Valid @RequestBody EmailSendForm emailSendForm) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = JSONObject.toJSONString(emailSendForm.toPo(EmailSend.class));
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("messageId", messageId);
        emailMap.put("messageData", messageData);
        emailMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend(BusConfig.EXCHANGE_NAME,
                BusConfig.EMAIL_ROUTING,
                emailMap);
        return Result.success();
    }
}