package com.aac.optics.wlg.dashboard.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MoldingDataParam  implements Serializable {

    private String machineName;
    private String paramName;
    private List<String> waferIds;
}
