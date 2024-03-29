package com.aac.optics.provider.organization.entity.vo;

import com.aac.optics.common.web.entity.vo.BaseVo;
import com.aac.optics.provider.organization.entity.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserVo extends BaseVo<User> {

    public UserVo(User user) {
        BeanUtils.copyProperties(user, this);
    }

    private String name;
    private String mobile;
    private String username;
    private String description;
    private String deleted;
    private Set<Long> roleIds;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
