package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.aac.optics.mold.toollife.entity.ToolMachineNo;
import com.aac.optics.mold.toollife.entity.UpdateSheetForm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface ToolInfoService extends IService<ToolInfo> {
    String phaseExcelData(InputStream in);

    List<ToolInfo> getToolInfo(String monitorNo);

    List<ToolInfo> getToolInfo(String monitorNo, String programName);

    boolean updateToolLifeInfo(List<ToolInfo> toolInfos);

    boolean addMachineToolLifeInfo(UpdateSheetForm updateSheetForm);

    Map<String, Boolean> getToolMaintainStatus(List<String> monitorNos);

    List<ToolMachineNo> getToolMachineNo(String monitorNo);

    List<ToolInfo> getToolInfoByMonitorNoAndMachineNo(String monitorNo, String machineNo);

    Map<String, Object> getAbnormalToolLifeRatio();

    List<Map<String, Object>> getAbnormalQty();
}