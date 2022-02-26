package com.aac.optics.dingtalk.notification.service.impl;

import com.aac.optics.dingtalk.notification.entity.*;
import com.aac.optics.dingtalk.notification.mapper.SendSalesDataMapper;
import com.aac.optics.dingtalk.notification.provider.DingTalkApi;
import com.aac.optics.dingtalk.notification.service.SendSalesDataService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Override
    public void sendSalesDataGroupMessage() throws ApiException {

        List<Map<String, String>> batchList = sendSalesDataMapper.getSalesDataBatch();
        if(batchList == null || batchList.size() == 0)
        {
            log.info("没有需要发送到钉钉群的销售数据");
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.0");

        DecimalFormat percentDecimalFormat = new DecimalFormat("0.0%");
        for(Map<String, String> batchMap: batchList)
        {
            String title = batchMap.get("TITLE");
            String batchId = batchMap.get("BATCH");
            String titleTime = batchMap.get("TITLE_TIME");
            String tabType = batchMap.get("TAB_TYPE");

            //获取销售数据明细
            List<ProductContent> productContentList = sendSalesDataMapper.getSalesProductContentByBatch(batchId);

            MarkdownGroupMessage markdownGroupMessage = new MarkdownGroupMessage();
            markdownGroupMessage.setTitle(title);
            markdownGroupMessage.addContent("日期：" + titleTime);
            for(ProductContent productContent : productContentList)
            {
                String productType = productContent.getTabProductType();
                String shipQty = productContent.getShipQty() != null ? decimalFormat.format(productContent.getShipQty()) : "-";
                String shipAmount =  productContent.getShipAmount() != null ? decimalFormat.format(productContent.getShipAmount()) : "-";
                String shipPlanQty = productContent.getShipPlanQty() != null ? decimalFormat.format(productContent.getShipPlanQty()) : "-";
                String shipPlanAmount = productContent.getShipPlanAmount() != null ? decimalFormat.format(productContent.getShipPlanAmount()) : "-";
                String shipQtyRate = productContent.getShipQtyRate() != null ? percentDecimalFormat.format(productContent.getShipQtyRate()) : "-";
                String shipAmountRate = productContent.getShipAmountRate() != null ? percentDecimalFormat.format(productContent.getShipAmountRate()) : "-";

                if("汇总".equals(productType)) {
                    markdownGroupMessage.addBlankLine();
                    markdownGroupMessage.addBlobContent(productType + "计划出货数量：" + shipPlanQty + " K");
                    markdownGroupMessage.addBlobContent(productType + "实际出货数量：" + shipQty + " K");
                    markdownGroupMessage.addBlobContent(productType + "出货数量达成：" + shipQtyRate);
                    markdownGroupMessage.addBlobContent(productType + "计划出货金额：" + shipPlanAmount + " K");
                    markdownGroupMessage.addBlobContent(productType + "实际出货金额：" + shipAmount + " K");
                    markdownGroupMessage.addBlobContent(productType + "出货金额达成：" + shipAmountRate);
                }
                else {
                    markdownGroupMessage.addContent(productType + "计划出货数量：" + shipPlanQty + " K");
                    markdownGroupMessage.addContent(productType + "实际出货数量：" + shipQty + " K");
                    markdownGroupMessage.addBlobContent(productType + "出货数量达成：" + shipQtyRate);
                    markdownGroupMessage.addContent(productType + "计划出货金额：" + shipPlanAmount + " K");
                    markdownGroupMessage.addContent(productType + "实际出货金额：" + shipAmount + " K");
                    markdownGroupMessage.addBlobContent(productType + "出货金额达成：" + shipAmountRate);

                }
            }
            //获取详情URL
            String tabUrl = sendSalesDataMapper.getUrlByTabType(tabType);
            if(StringUtils.isEmpty(tabUrl))
            {
                log.warn("类型【{}】详情地址URL未配置", tabType);
            }
            else {
                markdownGroupMessage.addContent("[查看详情](" + tabUrl + ")");
            }

            List<String> robotUrlList = sendSalesDataMapper.getRobotUrlByTabType(tabType);
            if(robotUrlList == null || robotUrlList.size() == 0)
            {
                log.error("类型{}机器人URL未配置，请确认");
                return;
            }

            for(String robotUrl : robotUrlList) {
                dingTalkApi.sendGroupRobotMessage(robotUrl, title, markdownGroupMessage.toString());
            }
            //更新发送状态
            sendSalesDataMapper.updateSalesProductContentSendFlag(batchId);
            log.info("销售数据【{}】推送到群", batchId);
        }
    }
}
