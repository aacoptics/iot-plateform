import request from '@/utils/request'



export function findProductionDayReportPage(conditions) {
    return request({
        url: '/wlg-report/productionReport/queryProductionDayReportByCondition',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        data: conditions
    })
}
