package com.aac.optics.provider.jira.service;


import cn.hutool.core.lang.tree.Tree;
import com.aac.optics.provider.jira.entity.JiraIssue;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public interface JiraService {
    JSONObject getSprintInfo(String boardId);

    List<Tree<String>> getSpringIssues(List<String> sprintIds, LocalDateTime sTime, LocalDateTime eTime);

    JSONArray getAllBoards();

    List<Tree<String>> getIssuesByTime(String boardId, String startTime, String endTime);

    JSONObject getSprintDetail(String sprintId);

    List<JiraIssue> getJiraIssue(String boardId, String startTime, String endTime);
}
