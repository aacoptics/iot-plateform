package com.aac.optics.ims.analysisfile.service;

import com.aac.optics.ims.analysisfile.entity.StatusLogBaseInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AnalysisImsServiceTest {

    @Autowired
    private AnalysisImsService analysisImsService;

    @Test
    void analysisImsFile() {
        Map<String, Object> res = analysisImsService.AnalysisImsFile("d:\\Documents and Settings\\60054916\\Desktop\\新建文件夹 (3)\\Statuslog - 19AL - 01D73A38C9C4DAA0.xml");
    }

    @Test
    void getBaseInfo() {

        StatusLogBaseInfo baseInfo = analysisImsService.GetBaseInfo(analysisImsService.AnalysisImsFile("d:\\Documents and Settings\\60054916\\Desktop\\新建文件夹 (3)\\Statuslog - 19AL - 01D73A38C9C4DAA0.xml"));
    }
}