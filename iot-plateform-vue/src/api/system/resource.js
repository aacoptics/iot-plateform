import request from '@/utils/request'

export function findResourceTree() {
    return request({
        url: '/organization/resource/all',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export function findRoleResource(roleId) {
    return request({
        url: '/organization/resource/role/' + roleId,
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

