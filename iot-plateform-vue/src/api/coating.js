import request from '@/utils/request'

export function getStatus() {
    return request({
        url: '/coating/getStatus',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export function getAlarmInfo() {
    return request({
        url: '/coating/getAlarmInfo',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}
