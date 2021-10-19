package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.aac.optics.mold.toollife.service.AbnormalToolService;
import com.aac.optics.mold.toollife.service.ProgramDetailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class AbnormalToolServiceImplTest {

    @Autowired
    AbnormalToolService abnormalToolService;

    @Autowired
    ProgramDetailService programDetailService;

    @Test
    void test(){
        Integer a = abnormalToolService.getAbnormalCount("2021-10-18 07:30:00", "2021-10-19 07:30:00");
        List<AbnormalTool> getAbnormalTools = abnormalToolService.getAbnormalTools();
        //List<ProgramDetail> reas = programDetailService.getLastDayOee("2021-10-17 07:30:00");

        //ProgramDetail reas1 = programDetailService.getAbnormalTotalTime("AC06124");
        abnormalToolService.saveAbnormalTool();
    }
}