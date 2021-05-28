import Cookies from 'js-cookie'

export function getUsername() {
    return Cookies.get('username')
}

export function setUsername(username) {
    return Cookies.set('username', username)
}

export function removeUsername() {
    return Cookies.remove('username')
}

export function getUserDetail() {
    return JSON.parse(localStorage.getItem('user_detail'))
}

export function setUserDetail(userDetail) {
    if (userDetail.password) {
        delete userDetail.password
    }
    return localStorage.setItem('user_detail', JSON.stringify(userDetail))
}

export function removeUserDetail() {
    return localStorage.removeItem('user_detail')
}


export function getAccessToken() {
    return Cookies.get('access_token')
}

export function setAccessToken(token) {
    return Cookies.set('access_token', token)
}

export function removeAccessToken() {
    return Cookies.remove('access_token')
}

export function getRefreshToken() {
    return Cookies.get('refresh_token')
}

export function setRefreshToken(token) {
    return Cookies.set('refresh_token', token)
}

export function removeRefreshToken() {
    return Cookies.remove('refresh_token')
}

export function getMenuInfo() {
    return JSON.parse(localStorage.getItem('menu_info'))
}

export function setMenuInfo(menuInfo) {
    return localStorage.setItem('menu_info', JSON.stringify(menuInfo))
}

export function removeMenuInfo() {
    return localStorage.removeItem('menu_info')
}

export function getMenuItems() {
    return JSON.parse(localStorage.getItem('menu_items'))
}

export function setMenuItems(menuItems) {
    return localStorage.setItem('menu_items', JSON.stringify(menuItems))
}

export function removeMenuItems() {
    return localStorage.removeItem('menu_items')
}
