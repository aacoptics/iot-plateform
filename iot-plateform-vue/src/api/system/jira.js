import request from '@/utils/request'

export function getIssuesTree(sprintIds) {
    return request({
        url: '/jira-server/jiraProvider/getSprintIssues',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: sprintIds
    })
}

export function getIssuesTreeByTime(boardId, startTime, endTime) {
    return request({
        url: '/jira-server/jiraProvider/getIssuesByTime',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            boardId: boardId,
            startTime: startTime,
            endTime: endTime
        }
    })
}

export function getAllBoards() {
    return request({
        url: '/jira-server/jiraProvider/getBoards',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export function getSprints(boardId) {
    return request({
        url: '/jira-server/jiraProvider/getSprintInfo',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            boardId: boardId
        }
    })
}
