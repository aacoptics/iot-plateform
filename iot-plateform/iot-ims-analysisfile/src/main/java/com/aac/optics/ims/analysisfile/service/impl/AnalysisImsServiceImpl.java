package com.aac.optics.ims.analysisfile.service.impl;

import com.aac.optics.ims.analysisfile.entity.StatusLogBaseInfo;
import com.aac.optics.ims.analysisfile.service.AnalysisImsService;
import com.aac.optics.ims.analysisfile.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
@Service
public class AnalysisImsServiceImpl implements AnalysisImsService {

    @Override
    public Map<String, Object> AnalysisImsFile(String xmlPath) {
        Map<String, Object> recipe;
        try {
            recipe = XmlUtil.xmlToMap(xmlPath);
        } catch (Exception e) {
            recipe = null;
            log.error("解析IMS XML文件报错，错误是：" + e.getMessage());
        }
        return recipe;
    }

    @Override
    public StatusLogBaseInfo GetBaseInfo(Map<String, Object> xmlMap){
        StatusLogBaseInfo baseInfo = new StatusLogBaseInfo();
        Field[] fields = baseInfo.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.set(baseInfo, xmlMap.get(field.getName()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return baseInfo;
    }
}