package com.aac.optics.provider.jira.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aac.optics.provider.jira.entity.IssueInfo;
import com.aac.optics.provider.jira.entity.ProcessInfo;
import com.aac.optics.provider.jira.service.JiraService;
import com.aac.optics.provider.jira.service.ProcessInfoService;
import com.aac.optics.provider.jira.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class JiraServiceImpl implements JiraService {

    @Value("${jira.username}")
    String username;

    @Value("${jira.password}")
    String password;

    @Value("${jira.baseUrl}")
    String baseUrl;

    @Autowired
    ProcessInfoService processInfoService;


    @Override
    public JSONObject getSprintInfo(String boardId) {
        //get请求带参数
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("state", "active");
        list.add(param1);
        return HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/board/" + boardId + "/sprint", list, username, password);
    }

    public JSONObject getIssuesByTime(String boardId, String startTime, String endTime, Integer startAt) {
        //get请求带参数
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        BasicNameValuePair param2 = new BasicNameValuePair("startAt", startAt.toString());
        BasicNameValuePair param3 = new BasicNameValuePair("jql", "created >= " +
                startTime
                + " AND created <= "
                + endTime
                + " ORDER BY priority DESC, updated DESC");
        list.add(param1);
        list.add(param2);
        list.add(param3);
        return HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/board/" + boardId + "/issue", list, username, password);
    }

    @Override
    public List<Tree<String>> getIssuesByTime(String boardId, String startTime, String endTime) {
        Integer startAt = 0;
        JSONObject res = getIssuesByTime(boardId, startTime, endTime, startAt);
        JSONArray issues = res.getJSONArray("issues");
        while (res.getJSONArray("issues").size() == 500) {
            startAt += 500;
            res = getIssuesByTime(boardId, startTime, endTime, startAt);
            issues.addAll(res.getJSONArray("issues"));
        }
        return getFinalTrees(issues, startTime, endTime);
    }

    private List<Tree<String>> getFinalTrees(JSONArray issues, String startTime, String endTime) {
        List<IssueInfo> sprintIssues = new ArrayList<>();
        Map<String, String> userMap = new HashMap<>();
        String SprintStartTime = "";
        for (Object issue : issues) {
            JSONObject issueJson = (JSONObject) issue;
            IssueInfo issueInfo = new IssueInfo();
            String username = "";
            String jobNumber = "";
            try {
                SprintStartTime = issueJson.getJSONObject("fields").getJSONObject("sprint").getString("startDate");
                SprintStartTime = SprintStartTime.substring(0, 10);
            }
            catch(Exception err){

            }
            try {
                username = issueJson.getJSONObject("fields").getJSONObject("assignee").getString("displayName");
                jobNumber = issueJson.getJSONObject("fields").getJSONObject("assignee").getString("name");
                userMap.put(jobNumber, username);
            } catch (Exception err) {

            }
            String issueType = "";
            try {
                issueType = issueJson.getJSONObject("fields").getJSONObject("customfield_14900").getString("value");
            } catch (Exception err) {

            }
            String status = issueJson.getJSONObject("fields").getJSONObject("status").getString("name");
            String issueSummary = issueJson.getJSONObject("fields").getString("summary");
            String issueKey = issueJson.getString("key");
            String ekpIssueNo = issueJson.getJSONObject("fields").getString("customfield_14901");
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+0800'");
            String createTimeStr = issueJson.getJSONObject("fields").getString("created");
            String updateTimeStr = issueJson.getJSONObject("fields").getString("updated");
            LocalDateTime createTime = LocalDateTime.parse(createTimeStr, pattern);
            LocalDateTime updateTime = LocalDateTime.parse(updateTimeStr, pattern);
            Integer estimateTime = issueJson.getJSONObject("fields").getJSONObject("timetracking").getInteger("originalEstimateSeconds");
            Integer remainingTime = issueJson.getJSONObject("fields").getJSONObject("timetracking").getInteger("remainingEstimateSeconds");
            if (issueJson.getJSONObject("fields").getJSONArray("subtasks").size() > 0) {
                issueInfo.setHasSubTask(true);
            } else {
                issueInfo.setHasSubTask(false);
            }
            if (issueJson.getJSONObject("fields").containsKey("parent")) {
                issueInfo.setParentTaskKey(issueJson.getJSONObject("fields").getJSONObject("parent").getString("key"));
            } else {
                issueInfo.setParentTaskKey("-1");
            }
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            createTimeStr = df.format(createTime);
            updateTimeStr = df.format(updateTime);
            issueInfo.setUsername(username)
                    .setJobNumber(jobNumber)
                    .setIssueType(issueType)
                    .setIssueSummary(issueSummary)
                    .setIssueKey(issueKey)
                    .setEkpIssueNo(ekpIssueNo)
                    .setEstimateTime(estimateTime)
                    .setRemainingTime(remainingTime)
                    .setStatus(status)
                    .setCreateTime(createTimeStr)
                    .setUpdateTime(updateTimeStr);
            sprintIssues.add(issueInfo);
        }
        if(startTime != null){
            List<ProcessInfo> res = processInfoService.getProcessInfo(userMap.keySet(), startTime, endTime);
            log.info("111");


        }
        List<Tree<String>> finalRes = getIssueTrees(sprintIssues);
        return finalRes;
    }

    @Override
    public List<Tree<String>> getSpringIssues(String sprintId) {
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        list.add(param1);
        JSONObject res = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/sprint/" + sprintId + "/issue", list, username, password);

        JSONArray issues = res.getJSONArray("issues");
        return getFinalTrees(issues, null, null);
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
                    tree.putExtra("hasChildren", treeNode.isHasSubTask());
                    tree.putExtra("status", treeNode.getStatus());
                    tree.putExtra("createTime", treeNode.getCreateTime());
                    tree.putExtra("updateTime", treeNode.getUpdateTime());
                });
        return issues;
    }

    @Override
    public JSONArray getAllBoards() {
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        list.add(param1);
        JSONObject res = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/board", list, username, password);

        return res.getJSONArray("values");
    }

}
