package com.aac.optics.provider.jira.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aac.optics.provider.jira.entity.IssueInfo;
import com.aac.optics.provider.jira.service.JiraService;
import com.aac.optics.provider.jira.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class JiraServiceImpl implements JiraService {

    @Value("${jira.username}")
    String username;

    @Value("${jira.password}")
    String password;

    @Value("${jira.baseUrl}")
    String baseUrl;


    @Override
    public JSONObject getSprintInfo(String boardId) {
        //get请求带参数
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("state", "active");
        list.add(param1);
        return HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/board/" + boardId + "/sprint", list, username, password);
    }

    @Override
    public List<Tree<String>> getSpringIssues(String sprintId) {
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        list.add(param1);
        JSONObject res = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/sprint/" + sprintId + "/issue", list, username, password);

        JSONArray issues = res.getJSONArray("issues");
        List<IssueInfo> sprintIssues = new ArrayList<>();
        for (Object issue : issues) {
            JSONObject issueJson = (JSONObject) issue;
            IssueInfo issueInfo = new IssueInfo();
            String username = issueJson.getJSONObject("fields").getJSONObject("assignee").getString("displayName");
            String jobNumber = issueJson.getJSONObject("fields").getJSONObject("assignee").getString("name");
            String issueType = issueJson.getJSONObject("fields").getJSONObject("customfield_14900").getString("value");
            String issueSummary = issueJson.getJSONObject("fields").getString("summary");
            String issueKey = issueJson.getString("key");
            String ekpIssueNo = issueJson.getJSONObject("fields").getString("customfield_14901");
            Integer estimateTime = issueJson.getJSONObject("fields").getJSONObject("timetracking").getInteger("originalEstimateSeconds");
            Integer remainingTime = issueJson.getJSONObject("fields").getJSONObject("timetracking").getInteger("remainingEstimateSeconds");
            if (issueJson.getJSONObject("fields").getJSONArray("subtasks").size() > 0) {
                issueInfo.setHasSubTask(true);
            } else {
                issueInfo.setHasSubTask(false);
            }
            if (issueJson.getJSONObject("fields").containsKey("parent")) {
                issueInfo.setParentTaskKey(issueJson.getJSONObject("fields").getJSONObject("parent").getString("key"));
            }else{
                issueInfo.setParentTaskKey("-1");
            }
            issueInfo.setUsername(username)
                    .setJobNumber(jobNumber)
                    .setIssueType(issueType)
                    .setIssueSummary(issueSummary)
                    .setIssueKey(issueKey)
                    .setEkpIssueNo(ekpIssueNo)
                    .setEstimateTime(estimateTime)
                    .setRemainingTime(remainingTime);
            sprintIssues.add(issueInfo);
        }
        List<Tree<String>> finalRes = getIssueTrees(sprintIssues);
        return finalRes;
    }

    private List<Tree<String>> getIssueTrees(List<IssueInfo> sprintIssues) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都有默认值的哈
        // 默认支持排序
        treeNodeConfig.setParentIdKey("parentTaskKey");
        treeNodeConfig.setIdKey("issueKey");
        //转换器
        List<Tree<String>> issues = TreeUtil.build(sprintIssues, "-1", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getIssueKey());
                    tree.setParentId(treeNode.getParentTaskKey());
                    tree.setName(treeNode.getIssueKey());
                    // 扩展属性
                    tree.putExtra("username", treeNode.getUsername());
                    tree.putExtra("jobNumber", treeNode.getJobNumber());
                    tree.putExtra("issueType", treeNode.getIssueType());
                    tree.putExtra("issueSummary", treeNode.getIssueSummary());
                    tree.putExtra("ekpIssueNo", treeNode.getEkpIssueNo());
                    tree.putExtra("estimateTime", treeNode.getEstimateTime());
                    tree.putExtra("remainingTime", treeNode.getRemainingTime());
                });
        return issues;
    }

}
