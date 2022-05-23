package com.aac.optics.wlg.dashboard.service;

import com.aac.optics.wlg.dashboard.entity.MoldingMachineParamData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class MoldingMachineParamDataServiceTest {

    @Resource
    MoldingMachineParamDataService moldingMachineParamDataService;

    @Test
    void sendProductionDayDataNotification() {

        try {
            List<MoldingMachineParamData> test = moldingMachineParamDataService.getMoldingParamData("HPM-010",
                    "upper_moldcore_section_temp_actual_10", Arrays.asList("3852", "3851"));

            String a = "1";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}