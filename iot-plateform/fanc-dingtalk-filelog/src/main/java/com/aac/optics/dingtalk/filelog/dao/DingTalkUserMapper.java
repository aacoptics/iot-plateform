package com.aac.optics.dingtalk.filelog.dao;

import com.aac.optics.dingtalk.filelog.entity.DingTalkUser;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DingTalkUserMapper extends BaseMapper<DingTalkUser> {

    @DS("ekpDb")
    List<DingTalkUser> getDingtalkUserInfo();
}
