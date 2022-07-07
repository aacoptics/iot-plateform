import request from '@/utils/request'


export function getMachineName() {
    return request({
        url: '/wlg-iot/moldingMachineParam/getMoldingMachineName',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
    })
}

export function getWaferIds(machineName, startTime, endTime) {
    return request({
        url: '/wlg-iot/moldingMachineParam/getWaferIds',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            machineName: machineName,
            startTime: startTime,
            endTime: endTime
        }
    })
}

export function getMoldParamName(params) {
    return request({
        url: '/wlg-iot/moldingMachineParam/getMoldParamName',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: params
    })
}

export function getMoldParamValue(params) {
    return request({
        url: '/wlg-iot/moldingMachineParam/getMoldParamArray',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: params
    })
}
