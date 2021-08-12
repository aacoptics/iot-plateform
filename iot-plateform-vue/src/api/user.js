import request from '@/utils/request'
import * as qs from "qs";

export function login(loginInfo) {
    return request({
        url: '/auth-server/oauth/token',
        method: 'post',
        auth: {
            username: 'iot_client',
            password: '123456!iot'
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: qs.stringify(loginInfo),
        params: {
            grant_type: 'ldap'
        }
    })
}

export function logout() {
    return request({
        url: '/auth-server/oauth/logout',
        method: 'get'
    })
}

export function refreshToken(token) {
    return request({
        url: '/auth-server/oauth/token',
        method: 'post',
        auth: {
            username: 'iot_client',
            password: '123456!iot'
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        params: {
            grant_type: 'refresh_token',
            refresh_token: token
        }
    })
}

export function getUserInfo(username) {
    return request({
        url: '/organization/user',
        method: 'get',
        params: {
            uniqueId: username
        }
    })
}

export function getMenuByUsername(username) {
    return request({
        url: '/organization/menu/byUsername',
        method: 'get',
        params: {
            username: username
        }
    })
}
