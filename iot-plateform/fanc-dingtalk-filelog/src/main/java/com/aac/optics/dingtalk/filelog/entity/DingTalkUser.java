package com.aac.optics.dingtalk.filelog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("DINGTALK_USER")
public class DingTalkUser implements Serializable {

    private String fdName;

    private String name;

    private String jobNumber;

    private String fdId;

    private String userId;
}
