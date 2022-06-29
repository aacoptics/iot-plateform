package com.aac.optics.wlg.report.service.impl;

import com.aac.optics.wlg.report.entity.param.ProductionSummaryQueryParam;
import com.aac.optics.wlg.report.entity.po.ProductionSummary;
import com.aac.optics.wlg.report.entity.po.ProductionSummary;
import com.aac.optics.wlg.report.exception.BusinessException;
import com.aac.optics.wlg.report.mapper.ProductionSummaryMapper;
import com.aac.optics.wlg.report.mapper.ProductionSummaryMapper;
import com.aac.optics.wlg.report.service.ProductionSummaryService;
import com.aac.optics.wlg.report.service.ProductionSummaryService;
import com.aac.optics.wlg.report.util.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ProductionSummaryServiceImpl extends ServiceImpl<ProductionSummaryMapper, ProductionSummary> implements ProductionSummaryService {

    @Autowired
    private ProductionSummaryMapper productionSummaryMapper;

    @Override
    @Transactional
    public void importProductionSummaryExcel(InputStream in) throws IOException, InvalidFormatException {
        LocalDate currentLocalDate = LocalDate.now();
        //获取当月一号
        LocalDateTime monthStart = LocalDateTime.of(LocalDate.from(currentLocalDate.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);

        List<String[]> excelDataList = ExcelUtil.read(in).get(0);

        String[] titleArray = excelDataList.get(0);//标题行

        String projectNameTitle = titleArray[0];
        String requirementDateTitle = titleArray[1];
        if ((!"项目名".equals(projectNameTitle)) || (!"生产月份".equals(requirementDateTitle))) {
            throw new BusinessException("Excel模板错误，请确认!");
        }

        for (int i = 1; i < excelDataList.size(); i++) {
            String[] dataArray = excelDataList.get(i);
            if(dataArray == null || dataArray.length == 0)
            {
                break;
            }

            String projectName = dataArray[0]; //项目
            String productionDateStr = dataArray[1]; //需求月份
            String targetQtyStr = dataArray[2]; //目标生产数量
            String actualQtyStr = dataArray[3];//实际产量

            if (StringUtils.isEmpty(projectName)) {
                throw new BusinessException("第" + (i + 1) + "行，项目不能为空");
            }
            if (StringUtils.isEmpty(productionDateStr)) {
                throw new BusinessException("第" + (i + 1) + "行，日期不能为空");
            }
            if (StringUtils.isEmpty(targetQtyStr)) {
                throw new BusinessException("第" + (i + 1) + "行，目标生产数量不能为空");
            }
            if (StringUtils.isEmpty(actualQtyStr)) {
                throw new BusinessException("第" + (i + 1) + "行，实际生产数量不能为空");
            }
            if (StringUtils.isEmpty(productionDateStr)) {
                break;
            }
            BigDecimal targetQty = new BigDecimal(targetQtyStr);
            BigDecimal actualQty = new BigDecimal(actualQtyStr);

            LocalDate productionDate = null;
            try {
                productionDate = LocalDate.parse(productionDateStr+"-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                log.error("日期格式错误", e);
                throw new BusinessException("日期格式错误" + e.getMessage());
            }

            ProductionSummary productionSummary = null;
            if (monthStart.toLocalDate().isAfter(productionDate)) {
                continue;
            }
            else {
                productionSummary = this.queryProductionSummary(projectName, productionDate);
                if (productionSummary == null) {
                    productionSummary = new ProductionSummary();
                }
            }

            productionSummary.setProjectName(projectName);
            productionSummary.setProductionDate(productionDate);
            productionSummary.setTargetQty(targetQty);
            productionSummary.setActualQty(actualQty);

            this.saveOrUpdate(productionSummary);
        }
    }

    @Override
    public List<ProductionSummary> getAll() {
        return this.list();
    }

    @Override
    public IPage<ProductionSummary> query(Page page, ProductionSummaryQueryParam productionSummaryQueryParam) {
        QueryWrapper<ProductionSummary> queryWrapper = productionSummaryQueryParam.build();

        queryWrapper.eq(StringUtils.isNotBlank(productionSummaryQueryParam.getProjectName()), "project_name", productionSummaryQueryParam.getProjectName());

        if (productionSummaryQueryParam.getProductionDateStart() != null) {
            queryWrapper.ge("production_date", productionSummaryQueryParam.getProductionDateStart());
        }
        if (productionSummaryQueryParam.getProductionDateEnd() != null) {
            queryWrapper.le("production_date", productionSummaryQueryParam.getProductionDateEnd());
        }
        queryWrapper.orderByAsc("project_name");
        queryWrapper.orderByAsc("production_date");
        return this.page(page, queryWrapper);
    }


    @Override
    public ProductionSummary queryProductionSummary(String projectName, LocalDate productionDate) {
        QueryWrapper<ProductionSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name", projectName);
        queryWrapper.eq("production_date", productionDate);

        List<ProductionSummary> productionSummaryList = productionSummaryMapper.selectList(queryWrapper);
        if (productionSummaryList != null && productionSummaryList.size() > 0) {
            return productionSummaryList.get(0);
        }
        return null;
    }

    @Override
    public IPage<Map<String, Object>> queryProductionSummaryByCondition(Page page, ProductionSummaryQueryParam productionSummaryQueryParam) {

        page.addOrder(OrderItem.asc("project_name"));
        page.addOrder(OrderItem.asc("production_date"));

        IPage<Map<String, Object>> productionSummarys = productionSummaryMapper.queryProductionSummaryByCondition(page,
                productionSummaryQueryParam.getProjectName(),
                productionSummaryQueryParam.getProductionDateStart(),
                productionSummaryQueryParam.getProductionDateEnd());

        return productionSummarys;
    }


    @Override
    public boolean add(ProductionSummary productionSummary) {

        this.validationProductionSummary(productionSummary);

        boolean isSuccess = this.save(productionSummary);
        return isSuccess;
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(ProductionSummary productionSummary) {
        this.validationProductionSummary(productionSummary);

        boolean isSuccess = this.updateById(productionSummary);
        return isSuccess;
    }


    @Override
    public ProductionSummary get(Long id) {
        ProductionSummary productionSummary = this.getById(id);
        if (Objects.isNull(productionSummary)) {
            throw new BusinessException("data not found with id:" + id);
        }
        return productionSummary;
    }



    /**
     * 校验数据
     *
     * @param productionSummary
     */
    private void validationProductionSummary(ProductionSummary productionSummary)
    {
        String projectName = productionSummary.getProjectName();
        if(StringUtils.isEmpty(projectName))
        {
            throw new BusinessException("项目不能为空");
        }
    }

    @Override
    public int queryProductionSummaryCountByProjectName(String projectName) {
        QueryWrapper<ProductionSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name", projectName);

        Integer count = productionSummaryMapper.selectCount(queryWrapper);
        return count;
    }
}
