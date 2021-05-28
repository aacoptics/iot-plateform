package com.aac.optics.common.web.entity.param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.aac.optics.common.web.entity.po.BasePo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by yanshangqi
 */
@Data
public class BaseParam<T extends BasePo> {
    private LocalDateTime createdTimeStart;
    private LocalDateTime createdTimeEnd;

    public QueryWrapper<T> build() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(null != this.createdTimeStart, "created_time", this.createdTimeStart)
                .le(null != this.createdTimeEnd, "created_time", this.createdTimeEnd);
        return queryWrapper;
    }
}
