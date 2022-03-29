package com.aac.optics.wlg.report.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.report.entity.form.ProductionActualQueryForm;
import com.aac.optics.wlg.report.entity.param.ProductionActualQueryParam;
import com.aac.optics.wlg.report.service.ProductionActualService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/productionActual")
@Api("productionActual")
@Slf4j
public class ProductionActualController {

    @Autowired
    ProductionActualService productionActualService;


    @ApiOperation(value = "生产报表Excel上传", notes = "生产报表Excel上传")
    @ApiImplicitParam(name = "file", value = "Excel文件", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestPart("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();

        productionActualService.importProductionActualExcel(fileName, file.getInputStream());
        return Result.success();
    }


    @ApiOperation(value = "获取生产报表", notes = "获取生产报表")
    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(productionActualService.getAll());
    }


    @ApiOperation(value = "搜索生产报表", notes = "根据条件搜索生产报表信息")
    @ApiImplicitParam(name = "productionActualQueryForm", value = "生产报表查询参数", required = true, dataType = "ProductionActualQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/conditions")
    public Result conditions(@Valid @RequestBody ProductionActualQueryForm productionActualQueryForm) {
        log.debug("query with name:{}", productionActualQueryForm);
        return Result.success(productionActualService.queryProductionActualByCondition(productionActualQueryForm.getPage(),
                productionActualQueryForm.toParam(ProductionActualQueryParam.class)));
    }

}