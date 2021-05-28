import {login, getUserInfo} from '@/api/user'
import {
    getAccessToken, getRefreshToken,
    setAccessToken, setRefreshToken,
    getUsername, setUsername, setUserDetail
} from '@/utils/auth'

const state = {
    refreshToken: getRefreshToken(),
    accessToken: getAccessToken(),
    username: getUsername(),
    expireTime: localStorage.getItem('TokenExpire')
}

const mutations = {
    SET_ACCESS_TOKEN: (state, token) => {
        state.accessToken = token
    },

    SET_REFRESH_TOKEN: (state, token) => {
        state.refreshToken = token
    },

    SET_USER_NAME: (state, username) => {
        state.username = username
    },

    SET_TOKEN_EXPIRE: (state, expireTime) => {
        localStorage.setItem('TokenExpire', expireTime)
        state.expireTime = expireTime
    },
}

const actions = {
    // user login
    login({commit}, userInfo) {
        const {username, password} = userInfo
        return new Promise((resolve, reject) => {
            login({username: username.trim(), password: password}).then(response => {
                const {access_token, refresh_token, expires_in} = response
                setUsername(username.trim())
                setAccessToken(access_token)
                setRefreshToken(refresh_token)
                commit('SET_ACCESS_TOKEN', access_token)
                commit('SET_REFRESH_TOKEN', refresh_token)
                commit('SET_USER_NAME', username.trim())
                const curTime = new Date();
                const expireDate = new Date(curTime.setSeconds(curTime.getSeconds() + expires_in));
                commit('SET_TOKEN_EXPIRE', expireDate)
                localStorage.refreshTime = expireDate
                getUserInfo(username.trim()).then(response => {
                    const {data} = response
                    setUserDetail(data)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            }).catch(error => {
                reject(error)
            })
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
