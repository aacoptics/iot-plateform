package com.aac.optics.provider.jira.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aac.optics.provider.jira.entity.DashboardData;
import com.aac.optics.provider.jira.entity.IssueData;
import com.aac.optics.provider.jira.entity.IssueInfo;
import com.aac.optics.provider.jira.entity.JiraIssue;
import com.aac.optics.provider.jira.mapper.DashboardDataMapper;
import com.aac.optics.provider.jira.mapper.IssueDataMapper;
import com.aac.optics.provider.jira.service.ETLJiraService;
import com.aac.optics.provider.jira.service.JiraService;
import com.aac.optics.provider.jira.service.ProcessInfoService;
import com.aac.optics.provider.jira.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@DS("jiraDB")
public class ETLJiraServiceImpl implements ETLJiraService {

    @Value("${jira.username}")
    String username;

    @Value("${jira.password}")
    String password;

    @Value("${jira.baseUrl}")
    String baseUrl;

    @Autowired
    ProcessInfoService processInfoService;

    @Autowired
    DashboardDataMapper dashboardDataMapper;

    @Autowired
    IssueDataMapper issueDataMapper;

    public static Date addDateByDay(Date dateTime,int n) {

        //日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calstart = Calendar.getInstance();
        calstart.setTime(dateTime);

        calstart.add(Calendar.DAY_OF_WEEK, n);

        System.out.println(df.format(calstart.getTime()));
        return calstart.getTime();

    }

    @Override
    public String refreshDashboardData() {

        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        list.add(param1);
        JSONObject res = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/board", list, username, password);

        JSONArray resultArray = res.getJSONArray("values");
        for (Object boardObject : resultArray)
        {
            JSONObject boardJson = (JSONObject) boardObject;

            String boardName = boardJson.get("name").toString();
            String boardId = boardJson.get("id").toString();
            String boardLink = boardJson.get("self").toString();

            List<DashboardData> existDashBoardList = dashboardDataMapper.getDashboardByName(boardName);
            if(existDashBoardList != null && !existDashBoardList.isEmpty())
            {
                continue;
            }

            Map<String, Object> insertParam = new HashMap<>();
            insertParam.put("DASHBOARD_ID", Integer.parseInt(boardId));
            insertParam.put("DASHBOARD_NAME", boardName);
            insertParam.put("DASHBOARD_LINK", boardLink);

            dashboardDataMapper.insertDashboard(insertParam);
        }

        return "";
    }

    public JSONObject getIssuesAfterTime(String boardId, String startTime, Integer startAt) {
        //get请求带参数
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
        BasicNameValuePair param2 = new BasicNameValuePair("startAt", startAt.toString());
        BasicNameValuePair param3 = new BasicNameValuePair("jql", "created >= " +
                startTime
                + " ORDER BY priority DESC, updated DESC");
        list.add(param1);
        list.add(param2);
        list.add(param3);
        return HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/board/" + boardId + "/issue", list, username, password);
    }

    @Override
    public String initIssues() {
        List<Map<String,Object>> boardList = dashboardDataMapper.getAllDashboard();
        if(boardList == null || boardList.isEmpty())
        {
            return "看板列表为空，不能更新任务数据！";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(new Date());

        List<IssueData> existIssueList = issueDataMapper.getIssueByKey("");
        if(existIssueList == null || existIssueList.isEmpty())
        {
            startTime = "1900-01-01";
        }

        for(Map<String,Object> dashBoard: boardList)
        {
            Integer startAt = 0;

            String boardId = dashBoard.get("DASHBOARD_ID") + "";
            JSONObject res = getIssuesAfterTime(boardId, startTime, startAt);
            JSONArray issues = res.getJSONArray("issues");

            while (res.getJSONArray("issues").size() == 500) {
                startAt += 500;
                res = getIssuesAfterTime(boardId, startTime, startAt);
                issues.addAll(res.getJSONArray("issues"));
            }

            float hourF = (float) 3600;

            DateTimeFormatter pattern0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+0800'");

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            LocalDateTime startTimeLocal = LocalDateTime.parse(startTime + " 00:00:00", pattern0);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Object issueObject : issues)
            {
                JSONObject issueJson = (JSONObject) issueObject;

                String issueStartTime = "";
                String issueEndTime = "";

                String issueId = issueJson.getString("id");
                String issueKey = issueJson.getString("key");
                String issueName = issueJson.getJSONObject("fields").getString("summary");
                String ekpIssueNo = issueJson.getJSONObject("fields").getString("customfield_14901");
                if(ekpIssueNo == null || "null".equalsIgnoreCase(ekpIssueNo))
                {
                    ekpIssueNo = null;
                }

                String status = issueJson.getJSONObject("fields").getJSONObject("status").getString("name");

                String createTimeStr = issueJson.getJSONObject("fields").getString("created");
                String updateTimeStr = issueJson.getJSONObject("fields").getString("updated");
                LocalDateTime createTime = LocalDateTime.parse(createTimeStr, pattern);
                LocalDateTime updateTime = LocalDateTime.parse(updateTimeStr, pattern);

                String issueType = null;
                if(issueJson.getJSONObject("fields").getJSONObject("customfield_14900") != null)
                {
                    issueType = issueJson.getJSONObject("fields").getJSONObject("customfield_14900").getString("value");
                }
                String domain = null;
                if(issueJson.getJSONObject("fields").getJSONObject("customfield_14700") != null)
                {
                    domain = issueJson.getJSONObject("fields").getJSONObject("customfield_14700").getString("value");
                }

                String username = null;
                if(issueJson.getJSONObject("fields").getJSONObject("assignee") != null)
                {
                    username = issueJson.getJSONObject("fields").getJSONObject("assignee").getString("displayName");
                }
                Integer estimateTime = issueJson.getJSONObject("fields").getJSONObject("timetracking")
                        .getInteger("originalEstimateSeconds");
                JSONArray workLogs = issueJson.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs");
                if(workLogs.size() > 0)
                {
                    estimateTime = 0;
                    for (Object workLog : workLogs) {
                        JSONObject workLogJson = (JSONObject) workLog;
                        LocalDateTime workLogTime = DateUtil.parse(workLogJson.getString("created"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+0800'").toTimestamp().toLocalDateTime();
                        estimateTime += workLogJson.getInteger("timeSpentSeconds");
                    }
                }

                /*JSONArray subtasks = issueJson.getJSONObject("fields").getJSONArray("subtasks");
                if(subtasks != null && subtasks.size() > 0)
                {
                    for (Object subtask : subtasks) {
                        JSONObject subtaskJson = (JSONObject) subtask;

                    }
                }*/

                String businessCost = null;
                String businessOwner = null;
                String developCost = null;
                String developOwner = null;

                float estimateTimeF = 0.0f;
                if(estimateTime != null)
                {
                    estimateTimeF = (float) estimateTime;
                }

                if(issueKey.indexOf("DEV-") > -1)
                {
                    developCost = numberFormat.format(estimateTimeF/hourF);
                    developOwner = username;
                }
                else
                {
                    businessCost = numberFormat.format(estimateTimeF/hourF);
                    businessOwner = username;
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

                List<IssueData> issueList = issueDataMapper.getIssueByKey(issueKey);
                if(issueList == null || issueList.isEmpty())
                {
                    Map<String, Object> insertIssueParam = new HashMap<>();
                    insertIssueParam.put("ISSUE_ID", Integer.parseInt(issueId));
                    insertIssueParam.put("ISSUE_KEY", issueKey);
                    insertIssueParam.put("ISSUE_NAME", issueName);
                    insertIssueParam.put("EKP_ISSUE_NO", ekpIssueNo);
                    insertIssueParam.put("ISSUE_TYPE", issueType);
                    insertIssueParam.put("DOMAIN", domain);
                    insertIssueParam.put("STATUS", status);
                    insertIssueParam.put("START_TIME", issueStartTime);
                    insertIssueParam.put("END_TIME", issueEndTime);
                    insertIssueParam.put("BUSINESS_OWNER", businessOwner);
                    insertIssueParam.put("BUSINESS_COST",  businessCost != null?Double.parseDouble(businessCost):null);
                    insertIssueParam.put("DEVELOP_OWNER", developOwner);
                    insertIssueParam.put("DEVELOP_COST",  developCost != null?Double.parseDouble(developCost):null);

                    issueDataMapper.insertIssueData(insertIssueParam);

                    Map<String, Object> dashboardIssueParam = new HashMap<>();
                    dashboardIssueParam.put("DASHBOARD_ID", Integer.parseInt(boardId));
                    dashboardIssueParam.put("ISSUE_KEY", issueKey);
                    issueDataMapper.insertDashboardIssue(dashboardIssueParam);
                }
            }
        }
        return "SUCCESS";
    }

    @Override
    public String updateIssues() throws ParseException {
        Map<String,Object> emptyParam = new HashMap<>();

        float hourF = (float) 3600;
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+0800'");

        List<Map<String, Object>> issueList = issueDataMapper.getIssuesToUpdate(emptyParam);
        if(issueList != null || !issueList.isEmpty())
        {
            for(Map<String,Object> issueMap:issueList)
            {
                String issueEndTime = "";

                String issueId = issueMap.get("ISSUE_ID") + "";
                String issueKey = issueMap.get("ISSUE_KEY") + "";
                String ekpIssueNo = issueMap.get("EKP_ISSUE_NO") + "";
                String createTime = issueMap.get("CREATE_TIME") + "";
                String parentIdDB = issueMap.get("PARENT_ID") + "";
                Date createDate = sdf.parse(createTime);

                List<NameValuePair> list = new LinkedList<>();
                BasicNameValuePair param1 = new BasicNameValuePair("maxResults", "500");
                list.add(param1);

                JSONObject resultJSON = HttpClientUtil.doGet(baseUrl + "/rest/agile/1.0/issue/" + issueId, list, username, password);

                String status = "";
                if(resultJSON.getJSONObject("fields") == null)
                {
                    continue;
                }

                if (resultJSON.getJSONObject("fields").getJSONObject("status") != null) {
                    status = resultJSON.getJSONObject("fields").getJSONObject("status").getString("name");
                }

                String updateTimeStr = resultJSON.getJSONObject("fields").getString("updated");
                LocalDateTime updateTime = LocalDateTime.parse(updateTimeStr, pattern);

                Integer estimateTime = resultJSON.getJSONObject("fields").getJSONObject("timetracking")
                        .getInteger("originalEstimateSeconds");
                JSONArray workLogs = resultJSON.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs");

                Integer parentId = Integer.parseInt(parentIdDB);
                if(resultJSON.getJSONObject("fields").getJSONObject("parent") != null)
                {
                    String parentIDTxt = resultJSON.getJSONObject("fields").getJSONObject("parent").getString("id");
                    parentId = Integer.parseInt(parentIDTxt);
                }
                if(parentId != 0 && "".equals(ekpIssueNo))
                {
                    List<Map<String,Object>> parentIssue = issueDataMapper.getIssueByID(parentId);
                    if(parentIssue != null && parentIssue.size() > 0)
                    {
                        ekpIssueNo = parentIssue.get(0).get("EKP_ISSUE_NO") + "";
                    }
                }
                issueDataMapper.deleteWorkLog(issueKey);

                if(workLogs.size() > 0)
                {
                    estimateTime = 0;
                    for (Object workLog : workLogs)
                    {
                        JSONObject workLogJson = (JSONObject) workLog;
                        LocalDateTime workLogTime = DateUtil.parse(workLogJson.getString("created"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+0800'").toTimestamp().toLocalDateTime();
                        estimateTime += workLogJson.getInteger("timeSpentSeconds");

                        String startTimeStr = workLogJson.getString("started");
                        String workLogDate = startTimeStr.substring(0,10);
                        String workLogDesc = workLogJson.getString("comment");

                        String cost = numberFormat.format((float) workLogJson.getInteger("timeSpentSeconds")/hourF);
                        Map<String, Object> inertWorkLogParam = new HashMap<>();
                        inertWorkLogParam.put("issueKey", issueKey);
                        inertWorkLogParam.put("workLogDate", workLogDate);
                        inertWorkLogParam.put("workLogDesc", workLogDesc);
                        inertWorkLogParam.put("cost", Double.parseDouble(cost));

                        issueDataMapper.insertWorkLog(inertWorkLogParam);
                    }
                }

                if("完成".equals(status))
                {
                    issueEndTime = df.format(updateTime);
                }
                else
                {
                    issueEndTime = "";
                }

                String businessCost = issueMap.get("BUSINESS_COST") + "";
                String businessOwner = issueMap.get("BUSINESS_OWNER") + "";
                String developCost = issueMap.get("DEVELOP_COST") + "";
                String developOwner = issueMap.get("DEVELOP_OWNER") + "";

                float estimateTimeF = 0.0f;
                if(estimateTime != null)
                {
                    estimateTimeF = (float) estimateTime;
                }

                if(issueKey.indexOf("DEV-") > -1)
                {
                    developCost = numberFormat.format(estimateTimeF/hourF);
                }
                else
                {
                    businessCost = numberFormat.format(estimateTimeF/hourF);
                }
                if(!"".equals(ekpIssueNo))
                {
                    Integer startAt = 0;
                    JSONObject res;
                    JSONArray issureArray;

                    if(issueKey.indexOf("DEV-") > -1)
                    {
                        Date startDate = addDateByDay(createDate, -60);
                        String startTime1 = formatter.format(startDate);

                        String destBoardId = "";

                        res = getIssuesAfterTime("3302", startTime1, startAt);
                        issureArray = res.getJSONArray("issues");
                    }
                    else
                    {
                        String startTime1 = formatter.format(createDate);

                        res = getIssuesAfterTime("3285", startTime1, startAt);
                        issureArray = res.getJSONArray("issues");
                    }
                    if(issureArray != null && issureArray.size() > 0)
                    {
                        for(Object issueBean:issureArray)
                        {
                            JSONObject issueBeanJson = (JSONObject) issueBean;
                            String ekpNo = "NA";
                            if(issueBeanJson.getJSONObject("fields") == null || issueBeanJson.getJSONObject("fields").getString("customfield_14901") == null)
                            {
                                continue;
                            }
                            ekpNo = issueBeanJson.getJSONObject("fields").getString("customfield_14901");
                            if(!ekpNo.equalsIgnoreCase(ekpIssueNo))
                            {
                                continue;
                            }
                            else
                            {
                                String owner = issueBeanJson.getJSONObject("fields").getJSONObject("assignee").getString("displayName");
                                Integer estimateTime1 = issueBeanJson.getJSONObject("fields").getJSONObject("timetracking")
                                        .getInteger("originalEstimateSeconds");
                                JSONArray workLogs1 = issueBeanJson.getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs");
                                if(workLogs1.size() > 0)
                                {
                                    estimateTime1 = 0;
                                    for (Object workLog : workLogs1) {
                                        JSONObject workLogJson = (JSONObject) workLog;
                                        LocalDateTime workLogTime = DateUtil.parse(workLogJson.getString("created"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+0800'").toTimestamp().toLocalDateTime();
                                        estimateTime1 += workLogJson.getInteger("timeSpentSeconds");
                                    }
                                }

                                float estimateTimeF1 = 0.0f;
                                if(estimateTime1 != null)
                                {
                                    estimateTimeF1 = (float) estimateTime1;
                                }

                                if(issueKey.indexOf("DEV-") > -1)
                                {
                                    businessOwner = owner;
                                    businessCost = numberFormat.format(estimateTimeF1/hourF);
                                }
                                else
                                {
                                    developOwner = owner;
                                    developCost = numberFormat.format(estimateTimeF1/hourF);
                                }
                            }
                        }
                    }
                }

                Map<String, Object> updateParam = new HashMap<>();

                updateParam.put("issueKey", issueKey);
                updateParam.put("status", status);
                updateParam.put("endTime", !"".equals(issueEndTime)?issueEndTime:null);
                updateParam.put("finishFlag", "完成".equals(status)?1:0);
                updateParam.put("businessCost", businessCost != null?Double.parseDouble(businessCost):null);
                updateParam.put("businessOwner", !"".equals(businessOwner)?businessOwner:null);
                updateParam.put("developCost", developCost != null?Double.parseDouble(developCost):null);
                updateParam.put("developOwner", !"".equals(developOwner)?developOwner:null);
                updateParam.put("parentId", parentId !=0?parentId:null);
                updateParam.put("ekpIssueNo", ekpIssueNo);

                issueDataMapper.updateIssueLog(updateParam);

            }
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> filterIssuesByCondition(String boardId, String startTime, String endTime)
    {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("boardId", boardId);
        queryParam.put("startTime", startTime);
        queryParam.put("endTime", endTime);

        List<Map<String, Object>> resultList = issueDataMapper.filterIssuesByCondition(queryParam);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> filterIssues(String boardId, String startTime, String endTime)
    {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("boardId", boardId);
        queryParam.put("startTime", startTime);
        queryParam.put("endTime", endTime);

        List<Map<String, Object>> resultList = issueDataMapper.filterIssues(queryParam);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> findTOP10JIRA(String boardId, String startTime, String endTime)
    {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("boardId", Integer.parseInt(boardId));
        queryParam.put("startTime", startTime);
        queryParam.put("endTime", endTime);

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if(boardId.indexOf("3285") != 0)
        {
            resultList = issueDataMapper.findTOP10BusinessJIRA(queryParam);
        }
        else
        {
            resultList = issueDataMapper.findTOP10DevelopJIRA(queryParam);
        }
        return resultList;
    }
}
