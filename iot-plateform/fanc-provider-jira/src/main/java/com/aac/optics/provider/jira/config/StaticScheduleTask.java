package com.aac.optics.provider.jira.config;

import com.aac.optics.provider.jira.service.ETLJiraService;
import com.aac.optics.provider.jira.service.JiraService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class StaticScheduleTask {

    @Autowired
    public ETLJiraService etlJiraService;

    //3.添加定时任务
    @Scheduled(cron = "0 */120 * * * ?")
    //或直接指定时间间隔，例如：5秒
    public void refreshDashboards()
    {
        String resultMSG = etlJiraService.refreshDashboardData();
        System.out.println("任务时间: " + LocalDateTime.now() + " 任务信息：" + resultMSG);
    }

    //3.添加定时任务
    @Scheduled(cron = "0 */20 * * * ?")
    //或直接指定时间间隔，例如：5秒
    public void initIssues()
    {
        String resultMSG = etlJiraService.initIssues();
        System.out.println("任务时间: " + LocalDateTime.now() + " 任务initIssues()，信息：" + resultMSG);
    }

    //3.添加定时任务
    @Scheduled(cron = "0 */5 * * * ?")
    //或直接指定时间间隔，例如：5秒
    public void updateIssues()  {
        System.out.println("任务开始时间: " + LocalDateTime.now() + " 任务updateIssues()");
        String resultMSG = null;
        try {
            resultMSG = etlJiraService.updateIssues();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("任务时间: " + LocalDateTime.now() + " 任务信息updateIssues()，执行异常：" + e.getMessage());
        }
        System.out.println("任务时间: " + LocalDateTime.now() + " 任务updateIssues()，信息：" + resultMSG);
    }
}
