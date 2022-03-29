package com.aac.optics.wlg.report.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.report.entity.form.EstimateFpyQueryForm;
import com.aac.optics.wlg.report.entity.param.EstimateFpyQueryParam;
import com.aac.optics.wlg.report.service.EstimateFpyService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/estimateFpy")
@Api("estimateFpy")
@Slf4j
public class EstimateFpyController {

    @Autowired
    EstimateFpyService estimateFpyService;


    @ApiOperation(value = "预估直通率Excel上传", notes = "预估直通率Excel上传")
    @ApiImplicitParam(name = "file", value = "Excel文件", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestPart("file") MultipartFile file) throws Exception {

        estimateFpyService.importEstimateFpyExcel(file.getInputStream());
        return Result.success();
    }


    @ApiOperation(value = "获取预估直通率", notes = "获取预估直通率")
    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(estimateFpyService.getAll());
    }


    @ApiOperation(value = "搜索预估直通率", notes = "根据条件搜索预估直通率信息")
    @ApiImplicitParam(name = "estimateFpyQueryForm", value = "预估直通率查询参数", required = true, dataType = "EstimateFpyQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/conditions")
    public Result conditions(@Valid @RequestBody EstimateFpyQueryForm estimateFpyQueryForm) {
        log.debug("query with name:{}", estimateFpyQueryForm);
        return Result.success(estimateFpyService.queryEstimateFpyByCondition(estimateFpyQueryForm.getPage(), estimateFpyQueryForm.toParam(EstimateFpyQueryParam.class)));
    }

}