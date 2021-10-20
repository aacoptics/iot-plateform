package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.aac.optics.mold.toollife.provider.EmailProvider;
import com.aac.optics.mold.toollife.service.AbnormalToolService;
import com.aac.optics.mold.toollife.service.ProgramDetailService;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class AbnormalToolServiceImplTest {

    @Autowired
    AbnormalToolService abnormalToolService;

    @Autowired
    ProgramDetailService programDetailService;

    @Autowired
    EmailProvider emailProvider;

    @Test
    void test(){
        abnormalToolService.sendAbnormalEmail();
//        List<String> to = new ArrayList<>();
//
//        to.add("yanshangqi@aacoptics.com");
//        String subject = "test";
//        String emailContent = "test";
//        Map<String, Object> test = new HashMap<>();
//        test.put("to", to);
//        test.put("subject", subject);
//        test.put("emailContent", emailContent);
//        emailProvider.sendEmail(test);
//        Integer a = abnormalToolService.getAbnormalCount("2021-10-18 07:30:00", "2021-10-19 07:30:00");
//        List<AbnormalTool> getAbnormalTools = abnormalToolService.getAbnormalTools();
//        //List<ProgramDetail> reas = programDetailService.getLastDayOee("2021-10-17 07:30:00");
//
//        //ProgramDetail reas1 = programDetailService.getAbnormalTotalTime("AC06124");
//        abnormalToolService.saveAbnormalTool();
    }
}