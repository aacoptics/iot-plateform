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
