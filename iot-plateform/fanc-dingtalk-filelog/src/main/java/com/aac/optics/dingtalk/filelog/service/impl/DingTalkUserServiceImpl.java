package com.aac.optics.dingtalk.filelog.service.impl;

import com.aac.optics.dingtalk.filelog.mapper.DingTalkUserMapper;
import com.aac.optics.dingtalk.filelog.entity.DingTalkUser;
import com.aac.optics.dingtalk.filelog.service.DingTalkUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@DS("ekpDb")
public class DingTalkUserServiceImpl extends ServiceImpl<DingTalkUserMapper, DingTalkUser> implements DingTalkUserService {
    @Autowired
    DingTalkUserMapper dingTalkUserMapper;

    @Override
    public List<DingTalkUser> getDingtalkUserInfo() {
        return dingTalkUserMapper.getDingtalkUserInfo();
    }
}