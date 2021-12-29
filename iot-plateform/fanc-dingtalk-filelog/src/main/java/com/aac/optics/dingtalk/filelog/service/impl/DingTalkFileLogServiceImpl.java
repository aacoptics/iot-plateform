package com.aac.optics.dingtalk.filelog.service.impl;

import com.aac.optics.dingtalk.filelog.entity.DingTalkFileLog;
import com.aac.optics.dingtalk.filelog.mapper.DingTalkFileLogMapper;
import com.aac.optics.dingtalk.filelog.service.DingTalkFileLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Slf4j
public class DingTalkFileLogServiceImpl extends ServiceImpl<DingTalkFileLogMapper, DingTalkFileLog> implements DingTalkFileLogService {

    @Override
    public void add(DingTalkFileLog dingTalkFileLog) {
        this.save(dingTalkFileLog);
    }

    public void addBatch(List<DingTalkFileLog> dingTalkFileLogs) {
        this.saveBatch(dingTalkFileLogs);
    }

}