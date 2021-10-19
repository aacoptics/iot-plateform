package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.common.core.util.UserContextHolder;
import com.aac.optics.common.web.entity.po.BasePo;
import com.aac.optics.mold.toollife.dao.AbnormalToolMapper;
import com.aac.optics.mold.toollife.dao.ScrapedToolMapper;
import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.aac.optics.mold.toollife.entity.ScrapedTool;
import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.aac.optics.mold.toollife.service.AbnormalToolService;
import com.aac.optics.mold.toollife.service.ProgramDetailService;
import com.aac.optics.mold.toollife.service.ScrapedToolService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class AbnormalToolServiceImpl extends ServiceImpl<AbnormalToolMapper, AbnormalTool> implements AbnormalToolService {

    @Autowired
    ScrapedToolService scrapedToolService;

    @Autowired
    AbnormalToolMapper abnormalToolMapper;

    @Autowired
    ProgramDetailService programDetailService;

    public void saveAbnormalTool() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN.withHour(7).withMinute(30)).plusDays(-1);
        List<ScrapedTool> scrapedToolList = scrapedToolService.getScrapedList(startDateTime.format(df));

        for (ScrapedTool scrapedTool : scrapedToolList) {
            String _toolNo = scrapedTool.getCodeNo();
            AbnormalTool abnormalTool = new AbnormalTool();
            abnormalTool.setToolNo(_toolNo);
            abnormalTool.setMatCode(scrapedTool.getMatCode());
            abnormalTool.setMatName(scrapedTool.getMatName());
            abnormalTool.setLifeSalvage(scrapedTool.getLifeSalvage());
            abnormalTool.setScrapedTime(LocalDateTime.parse(scrapedTool.getCreateDate().substring(0, 19), df));
            ProgramDetail programDetail = programDetailService.getAbnormalTotalTime(_toolNo);
            if (programDetail == null)
                continue;
            abnormalTool.setRealLifeSalvage(programDetail.getTotalTime().toString());
            abnormalTool.setLastMachineNo(programDetail.getMachineNo());
            abnormalTool.setArea(programDetail.getProgramName());

            if (programDetail.getTotalTime() < Integer.valueOf(scrapedTool.getLifeSalvage())) {
                this.save(abnormalTool);
            }
        }
    }

    @Override
    public List<AbnormalTool> getAbnormalTools() {
        QueryWrapper<AbnormalTool> wrapper = new QueryWrapper<>();
        wrapper.eq("is_confirmed", 0)
                .orderByDesc("scraped_time");
        List<AbnormalTool> abnormalTools = list(wrapper);
        return abnormalTools;
    }

    @Override
    public void updateAbnormalReason(AbnormalTool abnormalTool) {
        abnormalTool.setCheckedTime(LocalDateTime.now());
        this.updateById(abnormalTool);
    }

    @Override
    public void updateConfirmInfo(AbnormalTool abnormalTool) {
        abnormalTool.setConfirmedTime(LocalDateTime.now());
        this.updateById(abnormalTool);
    }

    @Override
    public Integer getAbnormalCount(String startTime, String endTime) {
        QueryWrapper<AbnormalTool> wrapper = new QueryWrapper<>();
        wrapper.between("scraped_time", startTime, endTime);
        Integer count = abnormalToolMapper.selectCount(wrapper);
        return count;
    }
}