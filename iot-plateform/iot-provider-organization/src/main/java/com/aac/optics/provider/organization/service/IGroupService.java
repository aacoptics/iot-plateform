package com.aac.optics.provider.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.aac.optics.provider.organization.entity.param.GroupQueryParam;
import com.aac.optics.provider.organization.entity.po.Group;
import java.util.List;

public interface IGroupService extends IService<Group> {
    /**
     * 获取用户组
     *
     * @param id
     * @return
     */
    Group get(Long id);

    /**
     * 新增用户组
     *
     * @param group
     * @return true为新增成功
     */
    boolean add(Group group);

    /**
     * 查询用户组
     *
     * @return
     */
    List<Group> query(GroupQueryParam groupQueryParam);

    /**
     * 根据父id查询用户组
     *
     * @return
     */
    List<Group> queryByParentId(Long id);

    /**
     * 更新用户组信息
     *
     * @param group
     */
    boolean update(Group group);

    /**
     * 根据id删除用户组
     *
     * @param id
     */
    boolean delete(Long id);
}
