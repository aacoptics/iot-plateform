import axios from 'axios';
import {getAccessToken, getRefreshTime,
    setAccessToken, setExpireTime,
    setRefreshToken, getRefreshToken, removeLoginInfo} from "@/utils/auth";
import router from "@/router";
import {refreshToken} from "@/api/user";
import {ElMessage} from "element-plus";

const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    baseURL: 'http://10.69.76.40:8009'
});

service.interceptors.request.use(
    config => {
        if (getAccessToken()) {
            config.headers.Authorization = `Bearer ${getAccessToken()}`
        }
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response;
        }
        // else {
        //     return Promise.reject();
        // }
    },
    error => {
        if (error.response) {
            if (error.response.status === 401) {
                const curTime = new Date();
                const refreshTime = new Date(Date.parse(getRefreshTime()));
                if (getRefreshTime() && (curTime <= refreshTime)) {
                    return refreshToken(getRefreshToken()).then((response) => {
                        if (response.data.access_token) {
                            ElMessage.success('刷新Token成功! 加载中...')
                            const {access_token, refresh_token, expires_in} = response.data
                            setAccessToken(access_token)
                            setRefreshToken(refresh_token)
                            const curTime = new Date();
                            const expireDate = new Date(curTime.setSeconds(curTime.getSeconds() + expires_in));
                            setExpireTime(expireDate)
                            error.config.__isRetryRequest = true;
                            error.config.headers.Authorization = 'Bearer ' + access_token;                            // error.config 包含了当前请求的所有信息
                            return axios(error.config);
                        } else {
                            // 刷新token失败 清除token信息并跳转到登录页面
                            router.push('/login')
                        }
                    });
                } else {
                    // 返回 401，并且不知用户操作活跃期内 清除token信息并跳转到登录页面
                    if(error.response.data.msg){
                        ElMessage.error(error.response.data.msg)
                    }
                    removeLoginInfo()
                    router.push('/login')
                }
            }
            // 403 无权限
            if (error.response.status === 403) {
                if(error.response.data.msg){
                    ElMessage.error(error.response.data.msg)
                }else {
                    ElMessage.error('失败！该操作无权限')
                }
                return Promise.reject();
            }
        }
        console.log(error);
        if(error.response.data.msg){
            ElMessage.error(error.response.data.msg)
        }
        return error.response; // 返回接口返回的错误信息
    }
);

export default service;
