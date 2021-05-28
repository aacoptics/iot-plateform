import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};

//https://www.cnblogs.com/laozhang-is-phi/p/10462316.html
export const saveRefreshTime = () => {
    let nowTime = new Date();
    let lastRefreshTime = localStorage.refreshTime ? new Date(localStorage.refreshTime) : new Date(-1);
    let expireTime = new Date(Date.parse(localStorage.TokenExpire))

    let refreshCount = 1; //滑动系数
    if (lastRefreshTime >= nowTime) {
        lastRefreshTime = nowTime > expireTime ? nowTime : expireTime;
        lastRefreshTime.setMinutes(lastRefreshTime.getMinutes() + refreshCount);
        localStorage.refreshTime = lastRefreshTime;
    } else {
        localStorage.refreshTime = new Date(-1);
    }
};
