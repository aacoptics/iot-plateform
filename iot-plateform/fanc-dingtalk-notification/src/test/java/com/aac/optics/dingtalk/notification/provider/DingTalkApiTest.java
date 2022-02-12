package com.aac.optics.dingtalk.notification.provider;

import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DingTalkApiTest {

    @Autowired
    private DingTalkApi dingTalkApi;

    @Test
    void sendTextCorpConversation() {

        try {
            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();

            dingTalkApi.sendTextCorpConversation(accessToken, "15351344650", "test123");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    void sendCardCorpConversation()
    {
        try {
            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();

            dingTalkApi.sendCardCorpConversation(accessToken,"15351344650", "1月Lens销售出货达成",
                    "### 1月Lens销售出货达成  \n出货金额：100K  \n出货数量：1000K  \n交付达成率：50%",
                    "查看详情", "https://www.dingtalk.com");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}