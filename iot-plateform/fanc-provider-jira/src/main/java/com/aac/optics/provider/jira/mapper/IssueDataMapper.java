package com.aac.optics.provider.jira.mapper;

import com.aac.optics.provider.jira.entity.DashboardData;
import com.aac.optics.provider.jira.entity.IssueData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface IssueDataMapper extends BaseMapper<IssueData> {

    @DS("jiraDB")
    void insertIssueData(Map<String, Object> param);

    @DS("jiraDB")
    List<IssueData> getIssueByKey(String issueKey);

    @DS("jiraDB")
    void insertDashboardIssue(Map<String, Object> param);

    @DS("jiraDB")
    List<Map<String, Object>> getIssuesToUpdate(Map<String, Object> param);

    @DS("jiraDB")
    void updateIssueLog(Map<String, Object> param);

    @DS("jiraDB")
    List<Map<String, Object>> filterIssuesByCondition(Map<String, Object> param);

    @DS("jiraDB")
    List<Map<String, Object>> findTOP10BusinessJIRA(Map<String, Object> param);

    @DS("jiraDB")
    List<Map<String, Object>> findTOP10DevelopJIRA(Map<String, Object> param);

}