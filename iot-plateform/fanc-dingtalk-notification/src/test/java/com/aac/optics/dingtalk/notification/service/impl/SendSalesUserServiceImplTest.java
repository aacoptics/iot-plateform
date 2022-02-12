package com.aac.optics.dingtalk.notification.service.impl;

import com.aac.optics.dingtalk.notification.service.SendSalesDataService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SendSalesUserServiceImplTest {

    @Autowired
    private SendSalesDataService sendSalesDataService;

    @Test
    void sendSalesDataNotification() {

        try {
            sendSalesDataService.sendSalesDataNotification();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}