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


    @Test
    void createDingtalkTodo() {

        try {
            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();

            String unionId = dingTalkApi.getUnionId(accessToken, "15351344650");

            dingTalkApi.createDingtalkTodo(accessToken, unionId, "cost_02", "管理费用预实分析",
                    "管理费用预算与实际费用统计（测试）;时间：2022年3月;您负责部门费用如下;当月实际费用：￥14,616 K;当月预算金额：￥12,103 K;GAP节省费用：￥1,487 K;",
                    "https://ekp.aacoptics.com:8443/km/review/km_review_main/kmReviewMain.do?method=view&fdId=180bc020117039298c3c67d492d85141&fdTaskInstanceId=180bc0277d0882a3f8aed824d9998e91"
                    );
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Test
    void getDingtalkTodoTask() {

        try {
            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();

            String unionId = dingTalkApi.getUnionId(accessToken, "15351344650");

            dingTalkApi.getDingtalkTodoTask(accessToken, unionId, "cost_12");
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void deleteDingtalkTodoTask() {

        try {
            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();

            String unionId = dingTalkApi.getUnionId(accessToken, "15351344650");

            dingTalkApi.deleteDingtalkTodoTask(accessToken, unionId, "cost_01");
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}