package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.dao.ToolInfoMapper;
import com.aac.optics.mold.toollife.model.ToolInfo;
import com.aac.optics.mold.toollife.service.ToolInfoService;
import com.aac.optics.mold.toollife.util.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.tools.Tool;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ToolInfoServiceImpl extends ServiceImpl<ToolInfoMapper, ToolInfo> implements ToolInfoService {

    @Override
    public List<ToolInfo> phaseExcelData(InputStream in) {
        List<String[]> excelDataList;
        List<ToolInfo> toolInfos = new ArrayList<>();
        try {
            excelDataList = ExcelUtil.read(in).get(0);
            String workpiece = excelDataList.get(3)[2];
            String monitorNo = excelDataList.get(3)[12];
            String material = excelDataList.get(14)[17];
            Pattern isNum = Pattern.compile("^[\\d]*$");
            Pattern isSwo = Pattern.compile("^O[\\d]*$");
            String tempRoute = "";
            for (String[] strings : excelDataList) {
                if (!StringUtils.isBlank(strings[1]) & (isNum.matcher(strings[1]).matches() || isSwo.matcher(strings[1]).matches())) {
                    ToolInfo toolInfo = new ToolInfo();
                    toolInfo.setWorkpiece(workpiece)
                            .setMonitorNo(monitorNo)
                            .setMaterial(material);
                    if (!StringUtils.isBlank(strings[0])) {
                        toolInfo.setRoute(strings[0]);
                        tempRoute = strings[0];
                    } else {
                        toolInfo.setRoute(tempRoute);
                    }
                    toolInfo.setProgramName(strings[1])
                            .setToolName(strings[5])
                            .setToolDiameter(strings[7])
                            .setToolNo(strings[8])
                            .setType(strings[10])
                            .setMargin(strings[12])
                            .setToolValidLength(strings[13])
                            .setMaxDepth(strings[15])
                            .setWorkTime(strings[18])
                            .setCutDepth(strings[19])
                            .setFeed(strings[20])
                            .setRemark(strings[21])
                            .setCreateDateTime(LocalDateTime.now());
                    if (!strings[14].equals("#N/A"))
                        toolInfo.setBrand(strings[14]);

                    List<ToolInfo> ToolInfoInDb = getToolInfo(toolInfo.getMonitorNo(), toolInfo.getProgramName());
                    if (ToolInfoInDb.size() > 0) {
                        toolInfo.setId(ToolInfoInDb.get(0).getId());
                        this.updateById(toolInfo);
                    } else {
                        this.save(toolInfo);
                    }
                    toolInfos.add(toolInfo);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return toolInfos;
    }

    @Override
    public List<ToolInfo> getToolInfo(String monitorNo, String programName) {
        QueryWrapper<ToolInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("monitor_no", monitorNo)
                .eq("program_name", programName);
        return this.list(wrapper);
    }

    @Override
    public List<ToolInfo> getToolInfo(String monitorNo) {
        QueryWrapper<ToolInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("monitor_no", monitorNo);
        return this.list(wrapper);
    }


    @Transactional
    public boolean updateToolLifeInfo(List<ToolInfo> toolInfos) {
        try {
            for (ToolInfo toolInfo : toolInfos) {
                if (StringUtils.isBlank(toolInfo.getMachineNo()) &&
                        StringUtils.isBlank(toolInfo.getToolPos()) &&
                        StringUtils.isBlank(toolInfo.getToolCode()))
                    continue;
                updateInfo(toolInfo);
            }
            return true;
        } catch (Exception err) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private void updateInfo(ToolInfo toolInfo) {
        UpdateWrapper<ToolInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("machine_no", toolInfo.getMachineNo())
                .set("tool_pos", toolInfo.getToolPos())
                .set("tool_code", toolInfo.getToolCode())
                .eq("monitor_no", toolInfo.getMonitorNo())
                .eq("program_name", toolInfo.getProgramName());
        update(updateWrapper);
    }
}