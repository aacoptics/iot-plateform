package com.aac.optics.dingtalk.notification.provider;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DingTalkApi {
    @Value("${dingtalk.appKey}")
    private String appKey;
    @Value("${dingtalk.appSecret}")
    private String appSecret;
    @Value("${dingtalk.host}")
    private String BASE_API_CONTENT;//钉钉服务器地址

    public OapiGettokenResponse getAccessToken() throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(BASE_API_CONTENT + "/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();

        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        try {
            return client.execute(request);
        } catch (Exception e) {
            try {
                return client.execute(request);
            } catch (Exception ex) {
                return client.execute(request);
            }
        }
    }

    /**
     * 发送文本工作通知
     */
    public void sendTextCorpConversation(String accessToken, String useridList, String message) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(1186196480L);
        request.setUseridList(useridList);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(message);
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
        System.out.println(rsp.getBody());

    }


    /**
     * 发送卡片工作通知
     */
    public void sendCardCorpConversation(String accessToken, String useridList, String title, String markdown, String singleTile, String singleUrl) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(1186196480L);
        request.setUseridList(useridList);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();

        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.setMsgtype("action_card");
        msg.getActionCard().setTitle(title);
        msg.getActionCard().setMarkdown(markdown);
        msg.getActionCard().setSingleTitle(singleTile);
        msg.getActionCard().setSingleUrl(singleUrl);

        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
        log.info(rsp.getBody());
    }


}
