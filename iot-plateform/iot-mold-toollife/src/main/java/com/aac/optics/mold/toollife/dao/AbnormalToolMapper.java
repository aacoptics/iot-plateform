package com.aac.optics.mold.toollife.dao;

import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ScrapedTool;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AbnormalToolMapper extends BaseMapper<AbnormalTool> {

    @Select("<script>"
            +"select email from tb_mold_abnormal_email_info where enable = 1"
            +"</script>")
    List<String> getAbnormalEmailList();
}
