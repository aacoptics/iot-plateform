import request from '@/utils/request'

export function getAlarmDetail(startTime, endTime) {
    return request({
        url: '/lenspacker/getAlarmDetail',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            startTime: startTime,
            endTime: endTime
        }
    })
}

export function getAlarmCount(startTime, endTime) {
    return request({
        url: '/lenspacker/getAlarmCount',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            startTime: startTime,
            endTime: endTime
        }

    })
}

export function getMachineCapacity(startTime, endTime) {
    return request({
        url: '/lenspacker/getMachineCapacity',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            startTime: startTime,
            endTime: endTime
        }
    })
}
