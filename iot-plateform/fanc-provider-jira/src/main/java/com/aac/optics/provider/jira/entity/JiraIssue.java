package com.aac.optics.provider.jira.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JiraIssue implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String issue;

    private String issueKey;

    private String startTime;

    private String endTime;

    private String devlopCost;

    private String businessCost;

    private String status;
}