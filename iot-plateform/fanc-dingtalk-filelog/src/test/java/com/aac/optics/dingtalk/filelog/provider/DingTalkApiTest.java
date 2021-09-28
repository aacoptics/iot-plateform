package com.aac.optics.dingtalk.filelog.provider;

import com.aac.optics.dingtalk.filelog.entity.DingTalkUser;
import com.aac.optics.dingtalk.filelog.service.DingTalkUserService;
import com.dingtalk.api.response.OapiCspaceAuditlogListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DingTalkApiTest {
    @Autowired
    DingTalkApi dingTalkApi;

    @Autowired
    DingTalkUserService dingTalkUserService;

    @Test
    void getAccessToken() {
        List<DingTalkUser> test = dingTalkUserService.getDingtalkUserInfo();
        List<DingTalkUser> test1 = test;
    }

    @Test
    void getDingTalkFileLog() {
        try {
            List<DingTalkUser> opticsUsers = dingTalkUserService.getDingtalkUserInfo();


            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();

            LocalDateTime eTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN.withHour(23));
            LocalDateTime sTime = eTime.plusDays(-1);

            OapiCspaceAuditlogListResponse res = dingTalkApi.getDingTalkFileLog(accessToken,sTime,eTime,null,null);

            List<OapiCspaceAuditlogListResponse.AuditLogVO> listAll = new ArrayList<>();

            List<OapiCspaceAuditlogListResponse.AuditLogVO> list = res.getResult().getList();
            while(list != null && list.size() > 0){
                listAll.addAll(list);
                res = dingTalkApi.getDingTalkFileLog(accessToken,sTime,eTime,list.get(list.size() - 1).getGmtCreate(),Long.valueOf(list.get(list.size() - 1).getBizId()));
                list = res.getResult().getList();
            }

            Integer i = 0;
            if(listAll.size() > 0){
                for (OapiCspaceAuditlogListResponse.AuditLogVO auditLogVO : listAll) {
                    List<DingTalkUser> userInfo = opticsUsers.stream().filter(user -> user.getUserId().equals(auditLogVO.getUserid())).collect(Collectors.toList());
                    if(userInfo.size() > 0)
                        i++;
                }

            }
            log.info(i.toString());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}