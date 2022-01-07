package com.aac.optics.provider.jira.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aac.optics.provider.jira.entity.IssueInfo;
import com.aac.optics.provider.jira.entity.JiraIssue;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
//        BasicNameValuePair param1 = new BasicNameValuePair("state", "active");
//        list.add(param1);
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
        return getFinalTrees(issues, null, null);
    }

    @Override
    public List<JiraIssue> getJiraIssue(String boardId, String startTime, String endTime) {
        Integer startAt = 0;
        JSONObject res = getIssuesByTime(boardId, startTime, endTime, startAt);
        JSONArray issues = res.getJSONArray("issues");
        while (res.getJSONArray("issues").size() == 500) {
            startAt += 500;
            res = getIssuesByTime(boardId, startTime, endTime, startAt);
            issues.addAll(res.getJSONArray("issues"));
        }

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+0800'");

        DateTimeFormatter pattern0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeLocal = LocalDateTime.parse(startTime + " 00:00:00", pattern0);
        LocalDateTime endTimeLocal = LocalDateTime.parse(endTime + " 00:00:00", pattern0);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<JiraIssue> jiraIssueList = new ArrayList<>();

        for (Object issue : issues) {
            JSONObject issueJson = (JSONObject) issue;
            JiraIssue jiraIssue = new JiraIssue();

            String username = "";
            String issueStartTime = "";
            String issueEndTime = "";

            try {
                username = issueJson.getJSONObject("fields").getJSONObject("assignee").getString("displayName");
            } catch (Exception err) {

            }
            String status = issueJson.getJSONObject("fields").getJSONObject("status").getString("name");
            String issueSummary = issueJson.getJSONObject("fields").getString("summary");
            String issueKey = issueJson.getString("key");
            String ekpIssueNo = issueJson.getJSONObject("fields").getString("customfield_14901");

            String createTimeStr = issueJson.getJSONObject("fields").getString("created");
            String updateTimeStr = issueJson.getJSONObject("fields").getString("updated");
            LocalDateTime createTime = LocalDateTime.parse(createTimeStr, pattern);
            LocalDateTime updateTime = LocalDateTime.parse(updateTimeStr, pattern);

            Integer estimateTime = issueJson.getJSONObject("fields").getJSONObject("timetracking")
                                    .getInteger("originalEstimateSeconds");
            JSONArray workLogs = issueJson.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs");
            if(workLogs.size() > 0)
            {
                estimateTime = 0;
                for (Object workLog : workLogs) {
                    JSONObject workLogJson = (JSONObject) workLog;
                    LocalDateTime workLogTime = DateUtil.parse(workLogJson.getString("created"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+0800'").toTimestamp().toLocalDateTime();
                    if(workLogTime.isAfter(startTimeLocal) && workLogTime.isBefore(endTimeLocal))
                        estimateTime += workLogJson.getInteger("timeSpentSeconds");
                }
            }
            String businessCost = "";
            String devlopCost = "";

            float estimateTimeF = 0.0f;
            if(estimateTime != null)
            {
                estimateTimeF = (float) estimateTime;
            }
            float hourF = (float) 3600;

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);

            if(issueKey.indexOf("DEV-") > -1)
            {
                devlopCost = numberFormat.format(estimateTimeF/hourF) + " h";
            }
            else
            {
                businessCost = numberFormat.format(estimateTimeF/hourF) + " h";
            }

            issueStartTime = df.format(createTime);
            if("完成".equals(status))
            {
                issueEndTime = df.format(updateTime);
            }
            else
            {
                issueEndTime = "";
            }

            jiraIssue.setIssueKey(issueKey);
            jiraIssue.setEkpIssueNo(ekpIssueNo);
            jiraIssue.setIssue(issueSummary);
            jiraIssue.setUsername(username);
            jiraIssue.setStartTime(issueStartTime);
            jiraIssue.setEndTime(issueEndTime);
            jiraIssue.setBusinessCost(businessCost);
            jiraIssue.setDevlopCost(devlopCost);
            jiraIssue.setStatus(status);

            jiraIssueList.add(jiraIssue);
        }
        return jiraIssueList;
    }

    private List<Tree<String>> getFinalTrees(JSONArray issues, LocalDateTime startTime, LocalDateTime endTime) {
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
            String territory = "";
            try {
                territory = issueJson.getJSONObject("fields").getJSONObject("customfield_14700").getString("value");
            }catch(Exception err){

            }
            Integer estimateTime = issueJson.getJSONObject("fields").getJSONObject("timetracking").getInteger("originalEstimateSeconds");
            JSONArray workLogs = issueJson.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs");
            if(workLogs.size() > 0){
                estimateTime = 0;
                for (Object workLog : workLogs) {
                    JSONObject workLogJson = (JSONObject) workLog;
                    LocalDateTime workLogTime = DateUtil.parse(workLogJson.getString("created"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+0800'").toTimestamp().toLocalDateTime();
                    if(workLogTime.isAfter(startTime) && workLogTime.isBefore(endTime))
                        estimateTime += workLogJson.getInteger("timeSpentSeconds");
                }
            }
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

            if(status == "完成" && (updateTime.isBefore(startTime) || updateTime.isAfter(endTime)))
                continue;
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
                    .setUpdateTime(updateTimeStr)
                    .setTerritory(territory);
            List<IssueInfo> tempSprintIssues = sprintIssues.stream()
                    .filter(_sprint -> issueInfo.getIssueKey().equals(_sprint.getIssueKey()))
                    .collect(Collectors.toList());
            if(tempSprintIssues.size() > 0 ||
                    estimateTime == null ||
                    estimateTime == 0)
                continue;
            sprintIssues.add(issueInfo);
        }
//        if(startTime != null){
//            List<ProcessInfo> res = processInfoService.getProcessInfo(userMap.keySet(), startTime, endTime);
//        }
        List<Tree<String>> finalRes = getIssueTrees(sprintIssues);
        return finalRes;
    }

    @Override
    public List<Tree<String>> getSpringIssues(List<String> sprintIds, LocalDateTime sTime, LocalDateTime eTime) {
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        list.add(param1);
        JSONArray issues = new JSONArray();
        for (String sprintId : sprintIds) {
            JSONObject res = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/sprint/" + sprintId + "/issue", list, username, password);
            issues.addAll(res.getJSONArray("issues"));
        }

        return getFinalTrees(issues, sTime, eTime);
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
                    tree.putExtra("territory", treeNode.getTerritory());
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

    @Override
    public JSONObject getSprintDetail(String sprintId) {
        List<NameValuePair> list = new LinkedList<>();
        JSONObject res = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/sprint/" + sprintId, list, username, password);
        return res;
    }

}
