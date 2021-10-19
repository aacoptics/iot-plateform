package com.aac.optics.provider.jira.controller;

import cn.hutool.core.lang.tree.Tree;
import com.aac.optics.common.core.exception.SystemErrorType;
import com.aac.optics.common.core.vo.Result;
import com.aac.optics.provider.jira.service.JiraService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jiraProvider")
@Api("JiraProvider")
@Slf4j
public class JiraProviderController {
    @Autowired
    JiraService jiraService;


    @ApiOperation(value = "查询Sprint任务", notes = "查询Sprint任务")
    @ApiImplicitParam(name = "boardId", value = "boardId", required = true, dataType = "String")
    @GetMapping("/getSprintIssues")
    public Result getSprintIssues(@RequestParam("boardId") String boardId) {
        JSONObject currentSprint = jiraService.getSprintInfo(boardId);
        JSONArray SprintValues = currentSprint.getJSONArray("values");
        List<Tree<String>> issueTrees = new ArrayList<>();
        if(SprintValues.size() > 0){
            String sprintId = ((JSONObject)SprintValues.get(0)).getString("id");
            issueTrees = jiraService.getSpringIssues(sprintId);
        }else{
            return Result.fail("无活动的Sprint");
        }
       return Result.success(issueTrees);
    }

    @ApiOperation(value = "按时间查询任务", notes = "按时间查询任务")
    @ApiImplicitParam(name = "boardId", value = "boardId", required = true, dataType = "String")
    @GetMapping("/getIssuesByTime")
    public Result getIssuesByTime(@RequestParam("boardId") String boardId,
                                  @RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime) {
        return Result.success(jiraService.getIssuesByTime(boardId, startTime, endTime));
    }

    @ApiOperation(value = "查询所有看板", notes = "查询所有看板")
    @GetMapping("/getBoards")
    public Result getBoards() {
        return Result.success(jiraService.getAllBoards());
    }

}