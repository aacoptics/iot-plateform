package com.aac.optics.provider.jira.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class JiraServiceTest {

    @Autowired
    JiraService jiraService;

    @Test
    void getSprint() {
        List<String>sprintIds = new ArrayList<>();
        sprintIds.add("4641");
        LocalDateTime startTime = LocalDateTime.MAX;
        LocalDateTime endTime = LocalDateTime.MIN;
        for (String sprintId : sprintIds) {
            JSONObject sprintJson = jiraService.getSprintDetail(sprintId);
            LocalDateTime tempStartTime = DateUtil.parse(sprintJson.getString("startDate"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+08:00'").toTimestamp().toLocalDateTime();
            LocalDateTime tempEndTime = DateUtil.parse(sprintJson.getString("endDate"), "yyyy-MM-dd'T'hh:mm:ss.SSS'+08:00'").toTimestamp().toLocalDateTime();
            if(tempStartTime.isBefore(startTime))
                startTime = tempStartTime;
            if(tempEndTime.isAfter(endTime))
                endTime = tempEndTime;
        }

        jiraService.getSpringIssues(sprintIds, startTime, endTime);


        JSONObject test123 = jiraService.getSprintDetail("4641");
//        JSONArray SprintValues = test.getJSONArray("values");
//        if(SprintValues.size() > 0){
//            String sprintId = ((JSONObject)SprintValues.get(0)).getString("id");
//            List<Tree<String>> qwe = jiraService.getSpringIssues("4182");
//        }
//
//        JSONObject test1 = test;

        List<Tree<String>> test = jiraService.getIssuesByTime("3285", "2021-10-11", "2021-10-14");
        JSONObject currentSprint = jiraService.getSprintInfo("3423");
        JSONArray SprintValues = currentSprint.getJSONArray("values");
        List<Tree<String>> issueTrees = new ArrayList<>();
        if(SprintValues.size() > 0){
            String sprintId = ((JSONObject)SprintValues.get(0)).getString("id");
            //issueTrees = jiraService.getSpringIssues(sprintId);
        }else{
            //return Result.fail("无活动的Sprint");
        }
       // return Result.success(issueTrees);
    }

    @Test
    public void test(){

    }
}