package com.aac.optics.wlg.report.service.impl;

import com.aac.optics.wlg.report.entity.dingtalk.message.MarkdownGroupMessage;
import com.aac.optics.wlg.report.entity.po.DingTalkMessageHistory;
import com.aac.optics.wlg.report.mapper.DingTalkNotificationMapper;
import com.aac.optics.wlg.report.provider.DingTalkApi;
import com.aac.optics.wlg.report.service.DingTalkNotificationService;
import com.aac.optics.wlg.report.service.ProductionReportService;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
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
    public void sendProductionDayDataNotification(String groupType) throws ApiException {
        LocalDateTime currentTime = LocalDateTime.now().minusDays(1);
        //获取当月一号
        LocalDateTime monthStart = LocalDateTime.of(LocalDate.from(currentTime.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);


        String currentDate = currentTime.format(DateTimeFormatter.ofPattern(CURRENT_DATE_FORMAT));

        //1 获取数据
        List<Map<String, Object>> productionDataList = productionReportService.queryProductionDayDataByDate(monthStart.toLocalDate(),
                currentTime.toLocalDate());
        if(productionDataList == null || productionDataList.size() == 0)
        {
            log.info("没有需要推送到钉钉群的数据");
            return;
        }
        //2 获取机器人
        List<Map<String, String>> robotList = dingTalkNotificationMapper.findRobotListByType(groupType);
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

        log.info("WLG推送消息内容" + markdownGroupMessage.toString());
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

    @Override
    public void sendProductionDayDataImageNotification(String groupType) throws ApiException {
        LocalDateTime currentTime = LocalDateTime.now().minusDays(1);
        //获取当月一号
        LocalDateTime monthStart = LocalDateTime.of(LocalDate.from(currentTime.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
        LocalDateTime monthEnd = LocalDateTime.of(LocalDate.from(currentTime.with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);

        int month = currentTime.getMonthValue();
        int monthDay = currentTime.getDayOfMonth();


        String currentDate = currentTime.format(DateTimeFormatter.ofPattern(CURRENT_DATE_FORMAT));
        //1 获取数据
        List<Map<String, Object>> productionDataList = productionReportService.findProductionDayReportDataByDate(monthStart.toLocalDate(), monthEnd.toLocalDate(),
                currentTime.toLocalDate());
        if(productionDataList == null || productionDataList.size() == 0)
        {
            log.info("没有需要推送到钉钉群的数据");
            return;
        }
        List<Map<String, Object>> customerRequirementDataList = productionReportService.findCustomerRequirementDataByDate(monthStart.toLocalDate());

        //2 获取机器人
        List<Map<String, String>> robotList = dingTalkNotificationMapper.findRobotListByType(groupType);
        if(robotList == null || robotList.size() == 0)
        {
            log.info("没有维护机器人");
            return;
        }

        //3 导出成Excel
        //excel模板路径
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("excelTemplate/WLG产出报表模板.xlsx");

        String tempDir = System.getProperty("java.io.tmpdir");
        long currentTimeMillis = System.currentTimeMillis();
        String fileName = "WLG生产报表-" + currentTimeMillis + ".xlsx";
        String imageFileName = "WLG生产报表-" + currentTimeMillis + ".png";

        int productionDataSize = productionDataList.size();
        //读取excel模板
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);

            //读取了模板内所有sheet内容
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //替换表头中的日期
            XSSFRow titleRow = sheet.getRow(0);
            for(int i=6; i<=10; i++)
            {
                XSSFCell titleCell = titleRow.getCell(i);
                String cellValue = titleCell.getStringCellValue();
                String resultCellValue = cellValue.replace("当月", month+"月");
                String resultValue = resultCellValue.replace("前一天", month+"月" + monthDay + "日");
                titleCell.setCellValue(resultValue);
            }


            //如果这行没有了，整个公式都不会有自动计算的效果的
//            sheet.setForceFormulaRecalculation(true);
            for(int i=0; i<productionDataSize; i++)
            {
                Map<String, Object> productionDayMap = productionDataList.get(i);
                XSSFRow dataRow = sheet.createRow(i + 2);
                dataRow.createCell(1).setCellValue(productionDayMap.get("project_name") != null ? productionDayMap.get("project_name") + "" : "");
                dataRow.createCell(2).setCellValue(productionDayMap.get("product") != null ? productionDayMap.get("product") + "" : "");
                dataRow.createCell(3); //客户需求数量
                dataRow.createCell(4); //达成率
                dataRow.createCell(5).setCellValue(productionDayMap.get("mold") != null ? productionDayMap.get("mold") + "" : ""); //模具
                dataRow.createCell(6).setCellValue(productionDayMap.get("SUM_MONTH_JHLEIJI") != null ? productionDayMap.get("SUM_MONTH_JHLEIJI") + "" : ""); //计划总产出
                dataRow.createCell(7).setCellValue(productionDayMap.get("SUM_JHLEIJI") != null ? productionDayMap.get("SUM_JHLEIJI") + "" : ""); //计划累计产出（颗）
                dataRow.createCell(8).setCellValue(productionDayMap.get("sum_after_output_qty") != null ? productionDayMap.get("sum_after_output_qty") + "" : ""); //实际累计产出（颗）
                dataRow.createCell(9).setCellValue(productionDayMap.get("LEIJI_RATE") != null ? productionDayMap.get("LEIJI_RATE") + "" : ""); //实际累计产出达成率
                dataRow.createCell(10).setCellValue(productionDayMap.get("JHTOURU") != null ? productionDayMap.get("JHTOURU") + "" : ""); //计划投入模数（模）
                dataRow.createCell(11).setCellValue(productionDayMap.get("mold_press_input_qty") != null ? productionDayMap.get("mold_press_input_qty") + "" : ""); //实际投入模数（模）
                dataRow.createCell(12).setCellValue(productionDayMap.get("mold_press_output_qty") != null ? productionDayMap.get("mold_press_output_qty") + "" : ""); //实际产出模数（模）
                dataRow.createCell(13).setCellValue(productionDayMap.get("JHHDCHANCHU") != null ? productionDayMap.get("JHHDCHANCHU") + "" : ""); //计划产出（颗)
                dataRow.createCell(14).setCellValue(productionDayMap.get("YUGUMOYA") != null ? productionDayMap.get("YUGUMOYA") + "" : ""); //预估产出（颗）
                dataRow.createCell(15).setCellValue(productionDayMap.get("after_output_qty") != null ? productionDayMap.get("after_output_qty") + "" : ""); //实际产出（颗）
                dataRow.createCell(16).setCellValue(productionDayMap.get("CHANCHU_RATE") != null ? productionDayMap.get("CHANCHU_RATE") + "" : ""); //实际产出达成率
            }
            //填充客户需求数据
            if(customerRequirementDataList != null && customerRequirementDataList.size() > 0)
            {
                for(int i=0; i<productionDataSize; i++)
                {
                    Map<String, Object> productionDayMap = productionDataList.get(i);
                    String projectName = productionDayMap.get("project_name") != null ? productionDayMap.get("project_name") + "" : "";
                    for(int j=0; j<customerRequirementDataList.size(); j++)
                    {
                        Map<String, Object> customerRequirementDataMap = customerRequirementDataList.get(j);
                        if(projectName.equals(customerRequirementDataMap.get("project_name")))
                        {
                            XSSFRow dataRow = sheet.getRow(i + 2);
                            dataRow.getCell(3).setCellValue(customerRequirementDataMap.get("qty") != null ? customerRequirementDataMap.get("qty") + "" : "");
                            dataRow.getCell(4).setCellValue(customerRequirementDataMap.get("completion_rate") != null ? customerRequirementDataMap.get("completion_rate") + "" : "");
                            break;
                        }
                    }
                }
            }

            //合并单元格
            int startRow = 2;
            for(int k=0; k<productionDataSize; k++)
            {
                Map<String, Object> productionDayMap = productionDataList.get(k);
                String mold = productionDayMap.get("mold") != null ? productionDayMap.get("mold") + "" : "";
                if("汇总".equals(mold))
                {
                    //合并单元格
                    CellRangeAddress projectNameRegion = new CellRangeAddress(startRow, k+2, 1, 1);
                    sheet.addMergedRegion(projectNameRegion);
                    CellRangeAddress productRegion = new CellRangeAddress(startRow, k+2, 2, 2);
                    sheet.addMergedRegion(productRegion);

                    CellRangeAddress requirementRegion = new CellRangeAddress(startRow, k+2, 3, 3);
                    sheet.addMergedRegion(requirementRegion);

                    CellRangeAddress completionRateRegion = new CellRangeAddress(startRow, k+2, 4, 4);
                    sheet.addMergedRegion(completionRateRegion);
                    startRow = k+3;
                }
            }
            //合并单元格
            CellRangeAddress region = new CellRangeAddress(productionDataSize+1, productionDataSize+1, 1, 2);
            sheet.addMergedRegion(region);

            //添加单元格样式
            XSSFCellStyle xssfTitleCellStyle = xssfWorkbook.createCellStyle();
            xssfTitleCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            xssfTitleCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            xssfTitleCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            xssfTitleCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            xssfTitleCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            xssfTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            XSSFFont xssfTitleFont = xssfWorkbook.createFont();
            xssfTitleFont.setFontName("微软雅黑");
            xssfTitleFont.setBold(true);
            xssfTitleFont.setFontHeightInPoints((short) 11);
            xssfTitleCellStyle.setFont(xssfTitleFont);

            XSSFCellStyle xssfMoldCellStyle = xssfWorkbook.createCellStyle();
            xssfMoldCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            xssfMoldCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            xssfMoldCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            xssfMoldCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            xssfMoldCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            xssfMoldCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            XSSFFont xssfMoldFont = xssfWorkbook.createFont();
            xssfMoldFont.setFontName("微软雅黑");
            xssfMoldFont.setFontHeightInPoints((short) 10);
            xssfMoldCellStyle.setFont(xssfMoldFont);

            XSSFCellStyle xssfContentCellStyle = xssfWorkbook.createCellStyle();
            xssfContentCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            xssfContentCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            xssfContentCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            xssfContentCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            xssfContentCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
            xssfContentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            XSSFFont xssfContentFont = xssfWorkbook.createFont();
            xssfContentFont.setFontName("微软雅黑");
            xssfContentFont.setFontHeightInPoints((short) 10);
            xssfContentCellStyle.setFont(xssfContentFont);

            for(int j=0; j<productionDataSize; j++)
            {
                XSSFRow dataRow = sheet.getRow(j+2);
                for(int k=1; k<dataRow.getLastCellNum(); k++)
                {
                    XSSFCell cell = dataRow.getCell(k);
                    if(k>=1 && k<=2)
                    {
                        cell.setCellStyle(xssfTitleCellStyle);
                    }
                    else if(k>=3 && k<=5)
                    {
                        cell.setCellStyle(xssfMoldCellStyle);
                    }
                    else {
                        cell.setCellStyle(xssfContentCellStyle);
                    }
                }
            }

            //修改模板内容导出
            FileOutputStream out = new FileOutputStream(tempDir + "/" + fileName);
            xssfWorkbook.write(out);
            out.close();
        } catch (IOException e) {
            log.error("创建生产报表Excel异常", e);
            return;
        }

        //4 Excel转图片
        Workbook spireXlsWorkbook = new Workbook();
        spireXlsWorkbook.loadFromFile(tempDir + "/" + fileName);
        Worksheet worksheet = spireXlsWorkbook.getWorksheets().get(0);
        // Excel转为图片
        worksheet.saveToImage(tempDir + "/" + imageFileName);

        //5 推送图片到群
        DingTalkMessageHistory dingTalkMessageHistory = new DingTalkMessageHistory();
        dingTalkMessageHistory.setProductionDate(currentTime.toLocalDate());

        //获取token
        OapiGettokenResponse oapiGettokenResponse = dingTalkApi.getAccessToken();
        String accessToken = oapiGettokenResponse.getAccessToken();

        //上传图片
        String mediaId = dingTalkApi.uploadMedia(accessToken, "image", tempDir + "/" + imageFileName);
        if(StringUtils.isEmpty(mediaId))
        {
            log.error("上传图片到钉钉异常" + tempDir + "/" + imageFileName);
            return;
        }

        MarkdownGroupMessage markdownGroupMessage = new MarkdownGroupMessage();
        markdownGroupMessage.setTitle("WLG项目汇总产出数据（" + currentDate + "）");
        markdownGroupMessage.addContent("![WLG项目汇总产出数据](" + mediaId + ")");

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
