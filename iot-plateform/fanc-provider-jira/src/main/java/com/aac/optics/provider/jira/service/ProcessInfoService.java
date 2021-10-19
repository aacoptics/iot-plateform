package com.aac.optics.provider.jira.service;

import com.aac.optics.provider.jira.entity.ProcessInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProcessInfoService extends IService<ProcessInfo> {
    List<ProcessInfo> getProcessInfo(Set<String> users,
                                     String startTime,
                                     String endTime);

}