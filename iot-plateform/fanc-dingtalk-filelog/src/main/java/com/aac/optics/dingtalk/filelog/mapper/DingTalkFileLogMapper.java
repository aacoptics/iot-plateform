package com.aac.optics.dingtalk.filelog.mapper;

import com.aac.optics.dingtalk.filelog.entity.DingTalkFileLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DingTalkFileLogMapper extends BaseMapper<DingTalkFileLog> {

}
