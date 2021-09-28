package com.aac.optics.provider.jira.service;


import cn.hutool.core.lang.tree.Tree;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface JiraService {
    JSONObject getSprintInfo(String boardId);

    List<Tree<String>> getSpringIssues(String sprintId);
}
