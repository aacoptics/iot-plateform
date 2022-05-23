package com.aac.optics.wlg.dashboard.mapper;

import com.aac.optics.wlg.dashboard.entity.MoldingMachineParamData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface MoldingMachineParamDataMapper extends BaseMapper<MoldingMachineParamData> {
    List<MoldingMachineParamData> getMoldingParamData(@Param("machineName") String machineName,
                                                      @Param("paramName") String paramName,
                                                      @Param("waferIds") List<String> waferIds);
}