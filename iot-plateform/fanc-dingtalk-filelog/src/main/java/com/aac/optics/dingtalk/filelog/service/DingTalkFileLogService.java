package com.aac.optics.dingtalk.filelog.service;

import com.aac.optics.dingtalk.filelog.entity.DingTalkFileLog;

import java.util.List;

public interface DingTalkFileLogService {

    void add(DingTalkFileLog dingTalkFileLog);

    void addBatch(List<DingTalkFileLog> dingTalkFileLogs);
}
