import request from '@/utils/request'

export function insertPlanData(params) {
    return request({
        url: '/MessagePlan/insertPlanData',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: params
    });
}

export function insertPlanContact(planKey, userNoList) {
    return request({
        url: '/MessagePlan/insertPlanContact',
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {planKey:planKey,
                userNoList:userNoList}
    });
}

export function updatePlanData(params) {
    return request({
        url: '/MessagePlan/updatePlanData',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: params
    });
}

export function filterPlanData(planKey, planName) {
    return request({
        url: '/MessagePlan/filterPlanData',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            planKey: planKey,
            planName: planName
        }

    })
}

export function filterPlanJOB(planKey, planName, workDay) {
    return request({
        url: '/MessagePlan/filterPlanJOB',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            planKey: planKey,
            planName: planName,
            workDay: workDay
        }

    })
}

export function filterContactData(userName) {
    return request({
        url: '/MessagePlan/filterContactData',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            userName: userName
        }

    })
}

export function filterPlanContact(planKey) {
    return request({
        url: '/MessagePlan/filterPlanContact',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            planKey: planKey
        }

    })
}

export function excutePlan(planKey) {
    return request({
        url: '/MessagePlan/excutePlan',
        method: 'get',
        headers: {
            'Content-Type': 'application/json'
        },
        params: {
            planKey: planKey
        }

    });
}

