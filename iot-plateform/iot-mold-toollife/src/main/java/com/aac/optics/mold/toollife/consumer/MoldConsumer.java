//package com.aac.optics.mold.toollife.consumer;
//
//import com.aac.optics.mold.toollife.entity.MoldDataEntity;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//@Slf4j
//public class MoldConsumer {
//    public static List<MoldDataEntity> MoldDataDataList = new ArrayList<>();
//
//    public void handleMessage(Object result) {
//        JSONArray resJson = (JSONArray) JSONArray.toJSON(result);
//        List<MoldDataEntity> dataList = JSONObject.parseArray(resJson.toJSONString(), MoldDataEntity.class);
//        MoldDataDataList = dataList;
//    }
//}