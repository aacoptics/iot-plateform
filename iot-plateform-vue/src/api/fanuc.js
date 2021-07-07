import request from '@/utils/request'

export function getByFloor(floor) {
    return request({
        url: '/fanuc/getByFloor',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            floor: floor
        }
    })
}

export function getDetailInfo(machineName) {
    return request({
        url: '/fanuc/getDetailInfo',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            machineName: machineName
        }
    })
}

export function getCondData(startTime, endTime, machineName) {
    return request({
        url: '/fanuc/getCondData',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            startTime: startTime,
            endTime: endTime,
            machineName: machineName
        }
    })
}

export function getMonitData(startTime, endTime, machineName) {
    return request({
        url: '/fanuc/getMonitData',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            startTime: startTime,
            endTime: endTime,
            machineName: machineName
        }
    })
}

export function getAlarmData(startTime, endTime, machineName) {
    return request({
        url: '/fanuc/getAlarmData',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            startTime: startTime,
            endTime: endTime,
            machineName: machineName
        }
    })
}
