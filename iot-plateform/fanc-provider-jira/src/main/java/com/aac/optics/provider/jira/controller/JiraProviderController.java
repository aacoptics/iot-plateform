package com.aac.optics.provider.jira.controller;

import cn.hutool.core.date.DateUtil;
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
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
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
    @PostMapping("/getSprintIssues")
    public Result getSprintIssues(@RequestBody List<String> sprintIds ) {
//        JSONObject currentSprint = jiraService.getSprintInfo(boardId);
//        JSONArray SprintValues = currentSprint.getJSONArray("values");
  //      List<Tree<String>> issueTrees = new ArrayList<>();
//        if(SprintValues.size() > 0){
//            String sprintId = ((JSONObject)SprintValues.get(0)).getString("id");
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

        List<Tree<String>> issueTrees = jiraService.getSpringIssues(sprintIds,startTime,endTime);
//        }else{
//            return Result.fail("无活动的Sprint");
//        }
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

    @ApiOperation(value = "查询看板所有冲刺", notes = "查询看板所有冲刺")
    @GetMapping("/getSprintInfo")
    public Result getSprintInfo(@RequestParam String boardId) {
        return Result.success(jiraService.getSprintInfo(boardId));
    }

}