package com.aac.optics.dingtalk.notification.mapper;

import com.aac.optics.dingtalk.notification.entity.Content;
import com.aac.optics.dingtalk.notification.entity.ProductContent;
import com.aac.optics.dingtalk.notification.entity.SalesUser;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@Mapper
public interface SendSalesDataMapper extends BaseMapper<Content> {

    @DS("jiraDB")
    List<Content> getSalesContent();

    @DS("jiraDB")
    List<SalesUser> getSendUsersByType(@Param("tabType") String tabType);

    @DS("jiraDB")
    void updateSalesContentSendFlag(@Param("id") Integer id);

    @DS("jiraDB")
    List<Map<String, String>> getSalesDataBatch();

    @DS("jiraDB")
    List<ProductContent> getSalesProductContentByBatch(@Param("batchId") String batchId);

    @DS("jiraDB")
    void updateSalesProductContentSendFlag(@Param("batchId") String batchId);

    @DS("jiraDB")
    String getUrlByTabType(@Param("tabType") String tabType);

    @DS("jiraDB")
    List<String> getRobotUrlByTabType(@Param("tabType") String tabType);

}
