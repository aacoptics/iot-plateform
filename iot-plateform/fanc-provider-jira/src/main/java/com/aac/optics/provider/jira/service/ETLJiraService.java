package com.aac.optics.provider.jira.service;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ETLJiraService {

    String refreshDashboardData();

    String initIssues();

    String updateIssues() throws ParseException;

    List<Map<String, Object>> filterIssuesByCondition(String boardId, String startTime, String endTime);

    List<Map<String, Object>> filterIssues(String boardId, String startTime, String endTime);

    /*Workbook exportList(String boardId, String startTime, String endTime);*/

    List<Map<String, Object>> findTOP10JIRA(String boardId, String startTime, String endTime);
}
