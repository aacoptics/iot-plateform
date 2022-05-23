package com.aac.optics.wlg.dashboard.service;

import com.aac.optics.wlg.dashboard.entity.MoldingMachineParamData;

import java.time.LocalDateTime;
import java.util.List;

public interface MoldingMachineParamDataService {

    List<MoldingMachineParamData> getMoldingParamData(String machineName,
                                                      String paramName,
                                                      List<String> waferIds);

    List<MoldingMachineParamData> getWaferIds(String machineName,
                                              LocalDateTime startTime,
                                              LocalDateTime endTime);

    List<MoldingMachineParamData> getMachineName();

    List<MoldingMachineParamData> getMoldingParamName(String machineName,
                                                      List<String> waferIds);
}
