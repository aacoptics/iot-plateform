package com.aac.optics.dingtalk.notification.scheduler;

import com.aac.optics.dingtalk.notification.service.SendSalesDataService;
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
    SendSalesDataService sendSalesDataService;

    @Scheduled(cron = "${dingtalk.cronStr.sendSalesDataNotificationCron}")
    @SchedulerLock(name = "sendSalesDataNotification", lockAtMostFor = "30m", lockAtLeastFor = "3m")
    public void sendSalesDataNotification() {
        log.info(LocalDateTime.now() + "开始执行推送销售数据异常定时任务");
        try {
            sendSalesDataService.sendSalesDataNotification();
        } catch (ApiException e) {
            log.error("推送销售数据异常", e);
        }
        log.info(LocalDateTime.now() + "完成执行推送销售数据异常定时任务");
    }
}