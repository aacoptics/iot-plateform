package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.model.ToolInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.io.InputStream;
import java.util.List;


public interface ToolInfoService extends IService<ToolInfo> {
    List<ToolInfo> phaseExcelData(InputStream in);

    List<ToolInfo> getToolInfo(String monitorNo);

    List<ToolInfo> getToolInfo(String monitorNo, String programName);

    boolean updateToolLifeInfo(List<ToolInfo> toolInfos);
}