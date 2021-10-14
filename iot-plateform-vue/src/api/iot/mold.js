import request from '@/utils/request'

export function uploadExcel(param) {
    const formData = new FormData()
    formData.append('file', param.file)
    return request({
        url: '/moldToolLife/toolLife/uploadExcel',
        method: 'post',
        data: formData
    })
}

export function getByMonitorNo(monitorNo) {
    return request({
        url: '/moldToolLife/toolLife/getByMonitorNo',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            monitorNo: monitorNo
        }
    })
}

export function updateToolInfo(param) {
    return request({
        url: '/moldToolLife/toolLife/updateToolInfo',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: param
    })
}

export function getMachineList() {
    return request({
        url: '/moldToolLife/toolLife/allMachine',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export function getMatInfoList() {
    return request({
        url: '/moldToolLife/toolLife/allMatInfo',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        }
    })
}

export function getToolMaintainStatus(param) {
    return request({
        url: '/moldToolLife/toolLife/getToolMaintainStatus',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: param
    })
}

export function getLastDayTotalTime(startTime) {
    return request({
        url: '/moldToolLife/toolLife/getLastDayOee',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            startTime: startTime
        }
    })
}

export function getLastDayScrapCount(startTime) {
    return request({
        url: '/moldToolLife/toolLife/getLastDayScrapCount',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            startTime: startTime
        }
    })
}
