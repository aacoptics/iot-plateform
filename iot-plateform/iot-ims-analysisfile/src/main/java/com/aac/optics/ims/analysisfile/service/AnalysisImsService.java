package com.aac.optics.ims.analysisfile.service;

import com.aac.optics.ims.analysisfile.entity.StatusLogBaseInfo;

import java.util.Map;

public interface AnalysisImsService {
    Map<String, Object> AnalysisImsFile(String xmlPath);

    StatusLogBaseInfo GetBaseInfo(Map<String, Object> xmlMap);
}
