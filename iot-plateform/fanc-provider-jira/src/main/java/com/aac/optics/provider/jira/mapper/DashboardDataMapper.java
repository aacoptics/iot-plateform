package com.aac.optics.provider.jira.mapper;

import com.aac.optics.provider.jira.entity.DashboardData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DashboardDataMapper extends BaseMapper<DashboardData> {

    @DS("jiraDB")
    List<Map<String,Object>> getAllDashboard();

    @DS("jiraDB")
    void insertDashboard(Map<String, Object> param);

    @DS("jiraDB")
    List<DashboardData> getDashboardByName(String DASHBOARD_NAME);
}