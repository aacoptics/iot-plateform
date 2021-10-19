package com.aac.optics.mold.toollife.scheduler;

import com.aac.optics.mold.toollife.service.AbnormalToolService;
import lombok.extern.slf4j.Slf4j;
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
    public void dingTalkSchedule() {
        abnormalToolService.saveAbnormalTool();
        log.info(LocalDateTime.now() + "保存异常数据成功！");
    }
}
