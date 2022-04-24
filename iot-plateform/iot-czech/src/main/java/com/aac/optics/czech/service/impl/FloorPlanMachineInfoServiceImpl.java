package com.aac.optics.czech.service.impl;

import com.aac.optics.czech.entity.FloorPlanMachineInfo;
import com.aac.optics.czech.entity.StatusInfo;
import com.aac.optics.czech.entity.TemperaturePlotInfo;
import com.aac.optics.czech.mapper.FloorPlanMachineInfoMapper;
import com.aac.optics.czech.service.FloorPlanMachineInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FloorPlanMachineInfoServiceImpl extends ServiceImpl<FloorPlanMachineInfoMapper, FloorPlanMachineInfo> implements FloorPlanMachineInfoService {

    @Autowired
    FloorPlanMachineInfoMapper floorPlanMachineInfoMapper;

    @Override
    public List<FloorPlanMachineInfo> getMachineInfoByFloor(int startNumber, int endNumber) {
        return floorPlanMachineInfoMapper.getMachineInfoByFloor(startNumber, endNumber);
    }

    @Override
    public List<FloorPlanMachineInfo> getAllMachineInfo() {
        return floorPlanMachineInfoMapper.getAllMachineInfo();
    }

    @Override
    public List<FloorPlanMachineInfo> getMachineInfoByFloorNumber(int floorNumber) {
        List<FloorPlanMachineInfo> machineInfoList = new ArrayList<FloorPlanMachineInfo>();
        switch(floorNumber) {
            case 1:
                machineInfoList = getMachineInfoByFloor(101, 116);
                break;
            case 2:
                machineInfoList = getMachineInfoByFloor(201, 216);
                break;
            case 3:
                machineInfoList = getMachineInfoByFloor(301, 316);
                break;
            case 4:
                machineInfoList = getMachineInfoByFloor(401, 416);
                break;
            case 5:
                machineInfoList = getMachineInfoByFloor(501, 516);
                break;
            case 6:
                machineInfoList = getMachineInfoByFloor(601, 616);
                break;
            case 7:
                machineInfoList = getMachineInfoByFloor(701, 716);
                break;
            case 8:
                machineInfoList = getMachineInfoByFloor(801, 816);
                break;
            case 9:
                machineInfoList = getMachineInfoByFloor(901, 916);
                break;
        }

        //根据机台号到对应的表里获取T2字段的值
        for(FloorPlanMachineInfo floorPlanMachineInfo : machineInfoList) {
            String machineNumber = floorPlanMachineInfo.getMachineNo();
            float t2 = floorPlanMachineInfoMapper.getTemperatureByMachineNo("G" + machineNumber);
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String temperature = decimalFormat.format(t2);
            floorPlanMachineInfo.setTemperature(temperature);
        }

        return machineInfoList;
    }

    @Override
    public FloorPlanMachineInfo getMachineInfoByMachineNumber(int machineNumber) {
        FloorPlanMachineInfo machineInfo = floorPlanMachineInfoMapper.getMachineInfoByMachineNumber(machineNumber);
        float t2 = floorPlanMachineInfoMapper.getTemperatureByMachineNo("G" + machineNumber);
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String temperature = decimalFormat.format(t2);
        machineInfo.setTemperature(temperature);
        return machineInfo;
    }

    @Override
    public List<TemperaturePlotInfo> getSpindleTemperature(String startTime, String endTime, int machineNumber) {
        return floorPlanMachineInfoMapper.getSpindleTemperature(startTime, endTime, "G" + machineNumber);
    }

    @Override
    public List<TemperaturePlotInfo> getAirTemperature(String startTime, String endTime, int machineNumber) {
        return floorPlanMachineInfoMapper.getAirTemperature(startTime, endTime, "A" + machineNumber);
    }

    @Override
    public List<TemperaturePlotInfo> getBearingTemperature(String startTime, String endTime, int machineNumber) {
        return floorPlanMachineInfoMapper.getBearingTemperature(startTime, endTime, "G" + machineNumber);
    }

    @Override
    public List<TemperaturePlotInfo> getMotorTemperature(String startTime, String endTime, int machineNumber) {
        return floorPlanMachineInfoMapper.getMotorTemperature(startTime, endTime, "G" + machineNumber);
    }

    @Override
    public List<StatusInfo> getStatusInfoByMachineNumber(int machineNumber) {
        return floorPlanMachineInfoMapper.getStatusInfoByMachineNumber(machineNumber);
    }
}
