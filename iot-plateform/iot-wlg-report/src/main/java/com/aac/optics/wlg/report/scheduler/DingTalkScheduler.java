package com.aac.optics.wlg.report.scheduler;

import com.aac.optics.wlg.report.service.DingTalkNotificationService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Component
public class DingTalkScheduler {

    @Resource
    DingTalkNotificationService dingTalkNotificationService;

    @Scheduled(cron = "${dingtalk.cronStr.sendProductionDayDataNotificationCron}")
    @SchedulerLock(name = "sendProductionDayDataNotification", lockAtMostFor = "30m", lockAtLeastFor = "3m")
    public void sendProductionDayDataNotification() {
        log.info(LocalDateTime.now() + "开始执行推送每日产出报告");
        try {
            dingTalkNotificationService.sendProductionDayDataNotification();
        } catch (ApiException e) {
            log.error("推送每日产出报告异常", e);
        }
        log.info(LocalDateTime.now() + "完成执行推送每日产出报告");
    }

}