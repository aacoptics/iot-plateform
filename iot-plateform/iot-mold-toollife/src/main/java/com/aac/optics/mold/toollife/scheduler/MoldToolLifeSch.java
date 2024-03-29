package com.aac.optics.mold.toollife.scheduler;

import com.aac.optics.mold.toollife.service.AbnormalToolService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class MoldToolLifeSch {
    @Autowired
    AbnormalToolService abnormalToolService;

    @Scheduled(cron = "${toolLife.scheduler.OneDayCron}")
    @SchedulerLock(name = "saveMoldToolLifeAbnormalTool", lockAtMostFor = "40m", lockAtLeastFor = "10m")
    public void dingTalkSchedule() {
        try {
            abnormalToolService.saveAbnormalTool();
            log.info("保存异常数据成功！");
        }catch(Exception err){
            log.error("保存异常数据报错" + err.getMessage());
        }
        try {
            abnormalToolService.sendAbnormalEmail();
            log.info("邮件发送成功！");
        }catch(Exception err){
            log.error("邮件发送报错" + err.getMessage());
        }
    }
}
