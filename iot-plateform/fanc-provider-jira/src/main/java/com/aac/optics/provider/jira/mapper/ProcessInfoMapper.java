package com.aac.optics.provider.jira.mapper;

import com.aac.optics.provider.jira.entity.ProcessInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Mapper
public interface ProcessInfoMapper extends BaseMapper<ProcessInfo> {

    List<ProcessInfo> getProcessInfo(@Param("users") Set<String> users,
                                     @Param("startTime") String startTime,
                                     @Param("endTime") String endTime);

}
