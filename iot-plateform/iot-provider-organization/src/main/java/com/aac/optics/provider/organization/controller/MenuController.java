package com.aac.optics.provider.organization.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.provider.organization.entity.form.MenuForm;
import com.aac.optics.provider.organization.entity.form.MenuQueryForm;
import com.aac.optics.provider.organization.entity.param.MenuQueryParam;
import com.aac.optics.provider.organization.entity.po.Menu;
import com.aac.optics.provider.organization.service.IMenuService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/menu")
@Api("menu")
@Slf4j
public class MenuController {

    @Autowired
    private IMenuService menuService;

//    @ApiOperation(value = "新增菜单", notes = "新增一个菜单")
//    @ApiImplicitParam(name = "menuForm", value = "新增菜单form表单", required = true, dataType = "MenuForm")
//    @PostMapping
//    public Result add(@Valid @RequestBody MenuForm menuForm) {
//        log.debug("name:{}", menuForm);
//        Menu menu = menuForm.toPo(Menu.class);
//        return Result.success(menuService.add(menu));
//    }
//
//    @ApiOperation(value = "删除菜单", notes = "根据url的id来指定删除对象")
//    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单ID", required = true, dataType = "Long")
//    @DeleteMapping(value = "/{id}")
//    public Result delete(@PathVariable Long id) {
//        return Result.success(menuService.delete(id));
//    }
//
//    @ApiOperation(value = "修改菜单", notes = "修改指定菜单信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, dataType = "Long"),
//            @ApiImplicitParam(name = "menuForm", value = "菜单实体", required = true, dataType = "MenuForm")
//    })
//    @PutMapping(value = "/{id}")
//    public Result update(@PathVariable Long id, @Valid @RequestBody MenuForm menuForm) {
//        Menu menu = menuForm.toPo(Menu.class);
//        menu.setId(id);
//        return Result.success(menuService.update(menu));
//    }
//
//    @ApiOperation(value = "获取菜单", notes = "获取指定菜单信息")
//    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单ID", required = true, dataType = "Long")
//    @GetMapping(value = "/{id}")
//    public Result get(@PathVariable Long id) {
//        log.debug("get with id:{}", id);
//        return Result.success(menuService.get(id));
//    }
//
//    @ApiOperation(value = "查询菜单", notes = "根据条件查询菜单信息，简单查询")
//    @ApiImplicitParam(paramType = "query", name = "name", value = "菜单名称", required = true, dataType = "string")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
//    )
//    @GetMapping
//    public Result query(@RequestParam String name) {
//        log.debug("query with name:{}", name);
//        MenuQueryParam menuQueryParam = new MenuQueryParam(name);
//        return Result.success(menuService.query(menuQueryParam));
//    }
//
//    @ApiOperation(value = "搜索菜单", notes = "根据条件查询菜单信息")
//    @ApiImplicitParam(name = "menuQueryForm", value = "菜单查询参数", required = true, dataType = "MenuQueryForm")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
//    )
//    @PostMapping(value = "/conditions")
//    public Result search(@Valid @RequestBody MenuQueryForm menuQueryForm) {
//        log.debug("search with menuQueryForm:{}", menuQueryForm);
//        return Result.success(menuService.query(menuQueryForm.toParam(MenuQueryParam.class)));
//    }
//
//    @ApiOperation(value = "根据父id查询菜单", notes = "根据父id查询菜单列表")
//    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单父ID", required = true, dataType = "Long")
//    @GetMapping(value = "/parent/{id}")
//    public Result search(@PathVariable Long id) {
//        log.debug("query with parent id:{}", id);
//        return Result.success(menuService.queryByParentId(id));
//    }

    @ApiOperation(value = "查询所有菜单", notes = "查询所有菜单信息")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @GetMapping(value = "/all")
    public Result queryAll() {
        return Result.success(menuService.getAll());
    }



    @ApiOperation(value = "根据用户查询菜单", notes = "根据用户查询菜单信息")
    @ApiImplicitParam(paramType = "queryByUserName", name = "username", value = "用户名", required = true, dataType = "string")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @GetMapping(value = "/byUsername")
    public Result queryByUserName(@RequestParam String username) {
        return Result.success(menuService.getByUsername(username));
    }

//        @GetMapping
//    public Result query(@RequestParam String name) {
//        log.debug("query with name:{}", name);
//        MenuQueryParam menuQueryParam = new MenuQueryParam(name);
//        return Result.success("");
//    }
}