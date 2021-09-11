import request from '@/utils/request'

export function findMenuTree() {
    return request({
        url: '/organization/menu/all',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export function findRoleMenus(roleId) {
    return request({
        url: '/organization/menu/byRole',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {roleId: roleId}
    })
}

