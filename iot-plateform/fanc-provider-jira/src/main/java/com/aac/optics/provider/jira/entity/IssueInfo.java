package com.aac.optics.provider.jira.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class IssueInfo implements Serializable {

    private String username;

    private String jobNumber;

    private String issueType;

    private String issueSummary;

    private String issueKey;

    private String ekpIssueNo;

    private Integer estimateTime;

    private Integer remainingTime;

    private boolean hasSubTask;

    private String parentTaskKey;
}