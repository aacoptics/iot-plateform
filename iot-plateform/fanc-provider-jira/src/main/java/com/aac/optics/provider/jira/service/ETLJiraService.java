package com.aac.optics.provider.jira.service;


import cn.hutool.core.lang.tree.Tree;
import com.aac.optics.provider.jira.entity.JiraIssue;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ETLJiraService {

    String refreshDashboardData();

    String initIssues();

    String updateIssues() throws ParseException;

    List<Map<String, Object>> filterIssuesByCondition(String boardId, String startTime, String endTime);

    Workbook exportList(String boardId, String startTime, String endTime);

    List<Map<String, Object>> findTOP10JIRA(String boardId, String startTime, String endTime);
}
