package com.aac.optics.provider.organization.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.provider.organization.entity.form.UserForm;
import com.aac.optics.provider.organization.entity.form.UserQueryForm;
import com.aac.optics.provider.organization.entity.form.UserUpdateForm;
import com.aac.optics.provider.organization.entity.param.UserQueryParam;
import com.aac.optics.provider.organization.entity.po.User;
import com.aac.optics.provider.organization.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api("user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增用户", notes = "新增一个用户")
    @ApiImplicitParam(name = "userForm", value = "新增用户form表单", required = true, dataType = "UserForm")
    @PostMapping
    public Result add(@Valid @RequestBody UserForm userForm) {
        log.debug("name:{}", userForm);
        User user = userForm.toPo(User.class);
        return Result.success(userService.add(user));
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象，逻辑删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.success(userService.delete(id));
    }

    @ApiOperation(value = "修改用户", notes = "修改指定用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userUpdateForm", value = "用户实体", required = true, dataType = "UserUpdateForm")})
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable Long id, @Valid @RequestBody UserUpdateForm userUpdateForm) {
        User user = userUpdateForm.toPo(User.class);
        user.setId(id);
        return Result.success(userService.update(user));
    }

    @ApiOperation(value = "获取用户", notes = "获取指定用户信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "Long")
    @GetMapping(value = "/{id}")
    public Result get(@PathVariable Long id) {
        log.debug("get with id:{}", id);
        return Result.success(userService.get(id));
    }

    @ApiOperation(value = "获取用户", notes = "根据用户唯一标识（username or mobile）获取用户信息")
    @ApiImplicitParam(paramType = "query", name = "uniqueId", value = "用户唯一标识", required = true, dataType = "string")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @GetMapping
    public Result query(@RequestParam String uniqueId) {
        log.debug("query with username or mobile:{}", uniqueId);
        return Result.success(userService.getByUniqueId(uniqueId));
    }

    @ApiOperation(value = "获取用户", notes = "根据用户名（username）获取用户信息")
    @ApiImplicitParam(paramType = "query", name = "username", value = "用户唯一标识", required = true, dataType = "string")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @GetMapping("/byName")
    public Result queryByUsername(@RequestParam String username) {
        log.debug("query with username or mobile:{}", username);
        return Result.success(userService.getByUsername(username));
    }

    @ApiOperation(value = "搜索用户", notes = "根据条件查询用户信息")
    @ApiImplicitParam(name = "userQueryForm", value = "用户查询参数", required = true, dataType = "UserQueryForm")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @PostMapping(value = "/conditions")
    public Result search(@Valid @RequestBody UserQueryForm userQueryForm) {
        log.debug("search with userQueryForm:{}", userQueryForm);
        return Result.success(userService.query(userQueryForm.getPage(), userQueryForm.toParam(UserQueryParam.class)));
    }
}