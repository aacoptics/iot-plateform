package com.aac.optics.wlg.dashboard.service;

import com.aac.optics.wlg.dashboard.entity.MoldingMachineParamData;

import java.util.List;

public interface MoldingMachineParamDataService {

    List<MoldingMachineParamData> getMoldingParamData(String machineName,
                                                      String paramName,
                                                      List<String> waferIds);
}
