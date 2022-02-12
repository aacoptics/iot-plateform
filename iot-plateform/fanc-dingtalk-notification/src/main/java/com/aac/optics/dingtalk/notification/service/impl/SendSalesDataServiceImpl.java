package com.aac.optics.dingtalk.notification.service.impl;

import com.aac.optics.dingtalk.notification.entity.Content;
import com.aac.optics.dingtalk.notification.entity.MarkdownCard;
import com.aac.optics.dingtalk.notification.entity.SalesUser;
import com.aac.optics.dingtalk.notification.mapper.SendSalesDataMapper;
import com.aac.optics.dingtalk.notification.provider.DingTalkApi;
import com.aac.optics.dingtalk.notification.service.SendSalesDataService;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SendSalesDataServiceImpl implements SendSalesDataService {


    @Resource
    DingTalkApi dingTalkApi;

    @Resource
    SendSalesDataMapper sendSalesDataMapper;

    /**
     * 发送销售数据工作通知
     *
     * @throws ApiException
     */
    public void sendSalesDataNotification() throws ApiException {
        //获取需要推送的内容
        List<Content> salesDataContentList = sendSalesDataMapper.getSalesContent();
        if (salesDataContentList == null || salesDataContentList.size() == 0) {
            log.info("没有需要发送的通知信息");
            return;
        }

        //获取token
        OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
        String accessToken = oapiGettokenResponse.getAccessToken();

        for (Content salesDataContent : salesDataContentList) {
            Integer contentId = salesDataContent.getId();
            String title = salesDataContent.getTitle();
            String content = salesDataContent.getContent();
            String tabType = salesDataContent.getTabType();
            String tabDate = salesDataContent.getTabDate();

            //获取需要推送的用户
            List<SalesUser> salesUserList = sendSalesDataMapper.getSendUsersByType(tabType);
            if (salesUserList == null || salesUserList.size() == 0) {
                log.warn("类型{}未配置推送用户", tabType);
                return;
            }

            String tabUrl = "";
            StringBuffer userIds = new StringBuffer();
            for (SalesUser salesUser : salesUserList) {
                tabUrl = salesUser.getTabUrl();

                String userId = salesUser.getUserid();
                userIds.append(userId + ",");
            }

            String markdownContent = this.convertContentToMarkdown(content);

            //发送销售数据工作通知
            dingTalkApi.sendCardCorpConversation(accessToken, userIds.toString(), title + "（" + tabDate + "）", markdownContent, "查看详情", tabUrl);

            log.info("销售数据【{}】推送到用户【{}】工作通知", contentId, userIds);
            //更新发送状态
            sendSalesDataMapper.updateSalesContentSendFlag(contentId);
        }

    }


    /**
     * 转换字符串为markdown格式
     *
     * @param content
     * @return
     */
    private String convertContentToMarkdown(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        String[] contentArray = content.split(";");

        MarkdownCard markdownCard = new MarkdownCard();
        markdownCard.setTitle(contentArray[0]);

        for (int i = 1; i < contentArray.length; i++) {
            markdownCard.addContent(contentArray[i]);
        }

        return markdownCard.toString();
    }
}
