package com.aac.optics.provider.jira.service.impl;

import com.aac.optics.provider.jira.dao.ProcessInfoMapper;
import com.aac.optics.provider.jira.entity.ProcessInfo;
import com.aac.optics.provider.jira.service.ProcessInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ProcessInfoServiceImpl extends ServiceImpl<ProcessInfoMapper, ProcessInfo> implements ProcessInfoService {

    @Autowired
    ProcessInfoMapper processInfoMapper;

    @Override
    public List<ProcessInfo> getProcessInfo(Set<String> users,
                                            String startTime,
                                            String endTime) {

        return processInfoMapper.getProcessInfo(users, startTime, endTime);
    }

}