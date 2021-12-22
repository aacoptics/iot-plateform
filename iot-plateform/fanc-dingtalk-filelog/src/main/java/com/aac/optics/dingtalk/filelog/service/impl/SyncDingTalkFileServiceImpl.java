package com.aac.optics.dingtalk.filelog.service.impl;

import com.aac.optics.dingtalk.filelog.entity.DingTalkFileLog;
import com.aac.optics.dingtalk.filelog.entity.DingTalkUser;
import com.aac.optics.dingtalk.filelog.provider.DingTalkApi;
import com.aac.optics.dingtalk.filelog.service.DingTalkFileLogService;
import com.aac.optics.dingtalk.filelog.service.DingTalkUserService;
import com.aac.optics.dingtalk.filelog.service.SyncDingTalkFileService;
import com.dingtalk.api.response.OapiCspaceAuditlogListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SyncDingTalkFileServiceImpl implements SyncDingTalkFileService {

    @Resource
    DingTalkUserService dingTalkUserService;

    @Resource
    DingTalkFileLogService dingTalkFileLogService;

    @Resource
    DingTalkApi dingTalkApi;

    public void GetDingTalkFileLog() {
        try {
            List<DingTalkUser> opticsUsers = dingTalkUserService.getDingtalkUserInfo();
            OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
            String accessToken = oapiGettokenResponse.getAccessToken();
            LocalDateTime eTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN.withHour(23));
            LocalDateTime sTime = eTime.plusDays(-1);
            OapiCspaceAuditlogListResponse res = dingTalkApi.getDingTalkFileLog(accessToken, sTime, eTime, null, null);
            List<DingTalkFileLog> listAll = new ArrayList<>();
            List<OapiCspaceAuditlogListResponse.AuditLogVO> list = res.getResult().getList();
            while (list != null && list.size() > 0) {
                for (OapiCspaceAuditlogListResponse.AuditLogVO auditLogVO : list) {
                    List<DingTalkUser> userInfo = opticsUsers.stream().filter(user -> user.getUserId().equals(auditLogVO.getUserid())).collect(Collectors.toList());
                    if (userInfo.size() > 0) {
                        DingTalkFileLog dingTalkFileLog = new DingTalkFileLog();
                        BeanUtils.copyProperties(auditLogVO, dingTalkFileLog);
                        dingTalkFileLog.setJobNumber(userInfo.get(0).getJobNumber());
                        dingTalkFileLog.setFdId(userInfo.get(0).getFdId());
                        dingTalkFileLog.setFdName(userInfo.get(0).getFdName());
                        listAll.add(dingTalkFileLog);
                    }
                }
                res = dingTalkApi.getDingTalkFileLog(accessToken, sTime, eTime, list.get(list.size() - 1).getGmtCreate(), Long.valueOf(list.get(list.size() - 1).getBizId()));
                list = res.getResult().getList();
            }
            List<List<DingTalkFileLog>> cutList = groupList(listAll, 4000);
            for (List<DingTalkFileLog> dingTalkFileLogs : cutList) {
                dingTalkFileLogService.addBatch(dingTalkFileLogs);
            }
            log.info("拉取钉钉文件完成！");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static <T> List<List<T>> groupList(List<T> list, int cutSize) {
        List<List<T>> listGroup = new ArrayList<>();
        int listSize = list.size();
        int toIndex = cutSize;
        for (int i = 0; i < list.size(); i += cutSize) {
            if (i + cutSize > listSize) {
                toIndex = listSize - i;
            }
            List<T> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }
}
