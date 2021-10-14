package com.aac.optics.mold.toollife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class AreaConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, String> areaInfo;

    private Set<String> areaCode;
}