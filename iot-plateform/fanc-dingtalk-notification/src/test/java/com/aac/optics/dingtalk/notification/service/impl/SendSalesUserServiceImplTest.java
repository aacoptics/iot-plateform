package com.aac.optics.dingtalk.notification.service.impl;

import com.aac.optics.dingtalk.notification.provider.FeishuApi;
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

    @Test
    void sendSalesDataGroupMessage()
    {
        try {
            sendSalesDataService.sendSalesDataGroupMessage();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private FeishuApi feishuApi;
    @Test
    void sendDataGroupMessage()
    {
        try {
            String asd = feishuApi.SendGroupMessage("https://open.feishu.cn/open-apis/bot/v2/hook/5260a48e-c8ba-4cd1-8dc6-93157054264a", "### **【诚瑞光学销售出货数据】**  \n" +
                    "日期：2022-03-24  \n" +
                    "Lens  \n" +
                    "日计划出货数量：3,018 K  \n" +
                    "日实际出货数量：8,349 K  \n" +
                    "**日出货数量达成：276.6%**  \n" +
                    "&nbsp;  \n" +
                    "当月计划出货数量：66,303 K  \n" +
                    "当月实际出货数量：45,612 K  \n" +
                    "其中南宁出货数量：12,918 K  \n" +
                    "**当月出货数量达成：68.8%**  \n" +
                    "当月计划出货金额：170,014 K  \n" +
                    "当月实际出货金额：108,654 K  \n" +
                    "其中南宁出货金额：27,599 K  \n" +
                    "**当月出货金额达成：63.9%**  \n" +
                    "&nbsp;  \n" +
                    "模组  \n" +
                    "日计划出货数量：144 K  \n" +
                    "日实际出货数量：488 K  \n" +
                    "**日出货数量达成：338.1%**  \n" +
                    "&nbsp;  \n" +
                    "当月计划出货数量：19,427 K  \n" +
                    "当月实际出货数量：12,105 K  \n" +
                    "**当月出货数量达成：62.3%**  \n" +
                    "当月计划出货金额：330,493 K  \n" +
                    "当月实际出货金额：201,621 K  \n" +
                    "**当月出货金额达成：61.0%**  \n" +
                    "&nbsp;  \n" +
                    "**汇总**  \n" +
                    "**日计划出货数量：3,163 K**  \n" +
                    "**日实际出货数量：8,836 K**  \n" +
                    "**日出货数量达成：279.4%**  \n" +
                    "&nbsp;  \n" +
                    "**当月计划出货数量：85,729 K**  \n" +
                    "**当月实际出货数量：57,717 K**  \n" +
                    "**当月出货数量达成：67.3%**  \n" +
                    "**当月计划出货金额：500,507 K**  \n" +
                    "**当月实际出货金额：310,276 K**  \n" +
                    "**当月出货金额达成：62.0%**  \n" +
                    "[查看详情](https://bi.aacoptics.com:8181/bi/aacoptics/tableau?http=https://tableau.aacoptics.com/trusted/&resource=/views/new_16407747512500/22?:refresh=y)  \n");
            String qwe = asd;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}