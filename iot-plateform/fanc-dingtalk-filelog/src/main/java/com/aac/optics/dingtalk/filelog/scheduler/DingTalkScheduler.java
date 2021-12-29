package com.aac.optics.dingtalk.filelog.scheduler;

import com.aac.optics.dingtalk.filelog.service.SyncDingTalkFileService;
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
    SyncDingTalkFileService syncDingTalkFileService;

    @Scheduled(cron = "${dingtalk.cronStr.syncDingTalkFileLogCron}")
    @SchedulerLock(name = "SyncDingTalkFileLog", lockAtMostFor = "30m", lockAtLeastFor = "5m")
    public void SyncDingTalkFileLog() {
        log.info(LocalDateTime.now() + "开始执行同步钉钉文件记录定时任务");
        syncDingTalkFileService.GetDingTalkFileLog();
        log.info(LocalDateTime.now() + "执行同步钉钉文件记录定时任务成功");
    }
}