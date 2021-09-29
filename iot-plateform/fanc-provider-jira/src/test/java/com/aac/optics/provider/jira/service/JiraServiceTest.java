package com.aac.optics.provider.jira.service;

import cn.hutool.core.lang.tree.Tree;
import com.aac.optics.common.core.vo.Result;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
//        JSONObject test = jiraService.getSprintInfo("3285");
//        JSONArray SprintValues = test.getJSONArray("values");
//        if(SprintValues.size() > 0){
//            String sprintId = ((JSONObject)SprintValues.get(0)).getString("id");
//            List<Tree<String>> qwe = jiraService.getSpringIssues("4182");
//        }
//
//        JSONObject test1 = test;

        JSONObject currentSprint = jiraService.getSprintInfo("3423");
        JSONArray SprintValues = currentSprint.getJSONArray("values");
        List<Tree<String>> issueTrees = new ArrayList<>();
        if(SprintValues.size() > 0){
            String sprintId = ((JSONObject)SprintValues.get(0)).getString("id");
            issueTrees = jiraService.getSpringIssues(sprintId);
        }else{
            //return Result.fail("无活动的Sprint");
        }
       // return Result.success(issueTrees);
    }
}