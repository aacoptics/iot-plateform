import request from '@/utils/request'

export function getIssuesTree(boardId) {
    return request({
        url: '/jira-server/jiraProvider/getSprintIssues',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {boardId: boardId}
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
