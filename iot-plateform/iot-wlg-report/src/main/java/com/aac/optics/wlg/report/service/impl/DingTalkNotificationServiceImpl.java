package com.aac.optics.wlg.report.service.impl;

import com.aac.optics.wlg.report.entity.dingtalk.message.MarkdownGroupMessage;
import com.aac.optics.wlg.report.entity.po.DingTalkMessageHistory;
import com.aac.optics.wlg.report.mapper.DingTalkNotificationMapper;
import com.aac.optics.wlg.report.provider.DingTalkApi;
import com.aac.optics.wlg.report.service.DingTalkNotificationService;
import com.aac.optics.wlg.report.service.ProductionReportService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DingTalkNotificationServiceImpl implements DingTalkNotificationService {

    private static final String CURRENT_DATE_FORMAT = "M月d日";

    @Autowired
    ProductionReportService productionReportService;

    @Autowired
    DingTalkNotificationMapper dingTalkNotificationMapper;

    @Resource
    DingTalkApi dingTalkApi;


    @Override
    public void sendProductionDayDataNotification() throws ApiException {
        LocalDateTime currentTime = LocalDateTime.now();

        String currentDate = currentTime.format(DateTimeFormatter.ofPattern(CURRENT_DATE_FORMAT));

        //1 获取数据
        List<Map<String, Object>> productionDataList = productionReportService.queryProductionDayDataByDate(currentTime.toLocalDate());
        if(productionDataList == null || productionDataList.size() == 0)
        {
            log.info("没有需要推送到钉钉群的数据");
            return;
        }
        //2 获取机器人
        List<Map<String, String>> robotList = dingTalkNotificationMapper.findRobotList();
        if(robotList == null || robotList.size() == 0)
        {
            log.info("没有维护机器人");
            return;
        }


        //3 构建markdown消息格式
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        MarkdownGroupMessage markdownGroupMessage = new MarkdownGroupMessage();
        markdownGroupMessage.setTitle("WLG项目汇总产出数据（" + currentDate + "）");

        for(Map<String, Object> productionData: productionDataList)
        {
            String projectName = productionData.get("project_name") != null ? productionData.get("project_name")+"" : "";
            String product = productionData.get("product") != null ? productionData.get("product")+"" : "";
            String sumAfterOutputQty = productionData.get("sum_after_output_qty") != null ? decimalFormat.format(new BigDecimal(productionData.get("sum_after_output_qty")+"")) : ""; //实际累计产出（颗）
            String sumInventoryQty = productionData.get("sum_inventory_qty") != null ? decimalFormat.format(new BigDecimal(productionData.get("sum_inventory_qty")+"")) : ""; //实际累计入库（颗）
            String afterOutputQty = productionData.get("after_output_qty") != null ? decimalFormat.format(new BigDecimal(productionData.get("after_output_qty")+"")) : ""; //实际外观检产出（颗）
            String inventoryQty = productionData.get("inventory_qty") != null ? decimalFormat.format(new BigDecimal(productionData.get("inventory_qty")+"")) : ""; //实际转镀膜入库（颗）
            String moldPressOutputQty = productionData.get("mold_press_output_qty") != null ? decimalFormat.format(new BigDecimal(productionData.get("mold_press_output_qty")+"")) : ""; //实际生产模数（模）
            String sumJHTouRu = productionData.get("SUM_JHTOURU") != null ? decimalFormat.format(new BigDecimal(productionData.get("SUM_JHTOURU")+"")) : ""; //
            String sumJHLeiJi = productionData.get("SUM_JHLEIJI") != null ? decimalFormat.format(new BigDecimal(productionData.get("SUM_JHLEIJI")+"")) : ""; //计划累计产出（颗）
            String jhTouRu = productionData.get("JHTOURU") != null ? decimalFormat.format(new BigDecimal(productionData.get("JHTOURU")+"")) : ""; //计划生产模数（模）
            String jhhdChanChu = productionData.get("JHHDCHANCHU") != null ? decimalFormat.format(new BigDecimal(productionData.get("JHHDCHANCHU")+"")) : ""; //计划产出（颗）
            String yuGuMoYa = productionData.get("YUGUMOYA") != null ? decimalFormat.format(new BigDecimal(productionData.get("YUGUMOYA")+"")) : ""; //预估模压产出（颗）
            String chanChuRate = productionData.get("CHANCHU_RATE") != null ? productionData.get("CHANCHU_RATE")+"" : ""; //实际产出达成率(实际外观检产出/计划产出）
            String leiJiRate = productionData.get("LEIJI_RATE") != null ? productionData.get("LEIJI_RATE")+"" : ""; //实际累计产出达成率（实际累计产出/计划累计产出）


            if("汇总".equals(projectName))
            {
                markdownGroupMessage.addBlobContent("1、单日产出数据");
                markdownGroupMessage.addContent("计划产出（颗）：" + jhhdChanChu);
                markdownGroupMessage.addContent("实际外观检产出（颗）：" + afterOutputQty);
                markdownGroupMessage.addContent("实际转镀膜入库（颗）：" + inventoryQty);
                markdownGroupMessage.addContent("实际产出达成率(实际外观检产出/计划产出）：" + chanChuRate);
                markdownGroupMessage.addBlobContent("2、累计产出数据");
                markdownGroupMessage.addContent("计划累计产出（颗）：" + sumJHLeiJi);
                markdownGroupMessage.addContent("实际累计产出（颗）：" + sumAfterOutputQty);
                markdownGroupMessage.addContent("实际累计入库（颗）：" + sumInventoryQty);
                markdownGroupMessage.addContent("实际累计产出达成率（实际累计产出/计划累计产出）：" + leiJiRate);
                markdownGroupMessage.addBlankLine();
            }
            else
            {
                if(StringUtils.isEmpty(product))
                {
                    markdownGroupMessage.addBlobContent("项目：" + projectName);
                }else
                {
                    markdownGroupMessage.addBlobContent("项目：" + projectName + "（" + product +"）");
                }
                markdownGroupMessage.addBlobContent("1、单日产出数据");
                markdownGroupMessage.addContent("计划生产模数（模）：" + jhTouRu);
                markdownGroupMessage.addContent("实际生产模数（模）：" + moldPressOutputQty);
                markdownGroupMessage.addContent("计划产出(颗)：" + jhhdChanChu);
                markdownGroupMessage.addContent("预估模压产出（颗）：" + yuGuMoYa);
                markdownGroupMessage.addContent("实际外观检产出（颗）：" + afterOutputQty);
                markdownGroupMessage.addContent("实际转镀膜入库（颗）：" + inventoryQty);
                markdownGroupMessage.addContent("实际产出达成率(实际外观检产出/计划产出）：" + chanChuRate);
                markdownGroupMessage.addBlobContent("2、累计产出数据");
                markdownGroupMessage.addContent("计划累计产出（颗）：" + sumJHLeiJi);
                markdownGroupMessage.addContent("实际累计产出（颗）：" + sumAfterOutputQty);
                markdownGroupMessage.addContent("实际累计入库（颗）：" + sumInventoryQty);
                markdownGroupMessage.addContent("实际累计产出达成率（实际累计产出/计划累计产出）：" + leiJiRate);
            }
        }

        //4 发送消息
        DingTalkMessageHistory dingTalkMessageHistory = new DingTalkMessageHistory();

        dingTalkMessageHistory.setProductionDate(currentTime.toLocalDate());

        for(Map<String, String> robotMap : robotList) {
            String robotId = robotMap.get("ROBOT_ID");
            String robotUrl = robotMap.get("ROBOT_URL");

            Map<String, String> resultMap = dingTalkApi.sendGroupRobotMessage(robotUrl, "WLG项目汇总产出数据", markdownGroupMessage.toString());
            String result = resultMap.get("result");
            String message = resultMap.get("message");
            //5 保存推送历史
            if (!StringUtils.isEmpty(message) && message.length() > 1024) {
                message = message.substring(1024);
            }
            dingTalkMessageHistory.setRobotId(robotId);
            dingTalkMessageHistory.setResult(result);
            dingTalkMessageHistory.setMessage(message);

            dingTalkNotificationMapper.insert(dingTalkMessageHistory);
        }

    }
}
