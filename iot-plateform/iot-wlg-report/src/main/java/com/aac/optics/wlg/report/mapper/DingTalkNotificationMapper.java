package com.aac.optics.wlg.report.mapper;

import com.aac.optics.wlg.report.entity.po.DingTalkMessageHistory;
import com.aac.optics.wlg.report.entity.po.ProductionPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DingTalkNotificationMapper extends BaseMapper<DingTalkMessageHistory> {

    List<Map<String, String>> findRobotListByType(@Param("groupType") String groupType);
}
