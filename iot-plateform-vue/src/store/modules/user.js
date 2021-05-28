import {login, getUserInfo} from '@/api/user'
import {
    getAccessToken, getRefreshToken,
    setAccessToken, setRefreshToken,
    getUsername, setUsername, setUserDetail, getUserDetail
} from '@/utils/auth'

const state = {
    refreshToken: getRefreshToken(),
    accessToken: getAccessToken(),
    username: getUsername()
}

const mutations = {
    SET_ACCESS_TOKEN: (state, token) => {
        state.accessToken = token
    },

    SET_REFRESH_TOKEN: (state, token) => {
        state.refreshToken = token
    }
}

const actions = {
    // user login
    login({commit}, userInfo) {
        const {username, password} = userInfo
        return new Promise((resolve, reject) => {
            login({username: username.trim(), password: password}).then(response => {
                const {access_token, refresh_token} = response
                setUsername(username.trim())
                setAccessToken(access_token)
                setRefreshToken(refresh_token)
                commit('SET_ACCESS_TOKEN', access_token)
                commit('SET_REFRESH_TOKEN', refresh_token)
                getUserInfo(username.trim()).then(response => {
                    const {data} = response
                    setUserDetail(data)

                    console.log(getUserDetail())
                }).catch(error => {
                    console.log(error)
                })
                resolve()
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
