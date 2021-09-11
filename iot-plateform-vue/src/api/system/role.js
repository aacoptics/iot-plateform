import request from '@/utils/request'

export function findPage(conditions) {
    return request({
        url: '/organization/role/conditions',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: conditions
    })
}

export function handleUpdate(updateForm) {
    return request({
        url: '/organization/role/' + updateForm.id,
        method: 'put',
        headers: {
            'Content-Type': 'application/json'
        },
        data: updateForm
    })
}

export function handleAdd(addForm) {
    return request({
        url: '/organization/role',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: addForm
    })
}

export function deleteRole(deleteForm) {
    return request({
        url: '/organization/role/' + deleteForm.id,
        method: 'delete',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}
