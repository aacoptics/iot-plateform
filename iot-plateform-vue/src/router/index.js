import {createRouter, createWebHistory} from "vue-router";
import Login from "../views/login/Login.vue";
import jira from "../views/system/permission/jira"
import routeMap from "./components"
import {setMenuInfo, setMenuItems, getUsername, getAccessToken, getMenuItems} from '@/utils/auth'
import {getMenuByUsername} from "@/api/system/user";
import {saveRefreshTime} from "@/api";

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    },
    {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: Login
    },
    {
        path: "/jiraDashboard",
        name: "jiraDashboard",
        meta: {
            title: 'jira看板',
            openView: true
        },
        component: jira
    }
]

let isFetchRemote = true;

const formatMenus = function (menuData, menuItems) {
    if (!menuItems) {
        menuItems = [];
    }
    menuData.length && menuData.forEach(menu => {
        const menuItem = {
            icon: menu.icon,
            index: menu.path ? menu.path.replace("/", "") : menu.name,
            title: menu.title
        }
        menuItems.push(menuItem)
        if (menu.children && menu.children.length) {
            menuItem.subs = [];
            formatMenus(menu.children, menuItem.subs);
        }
    });
    return menuItems;
}

const formatRoutes = function (routes, routeData) {
    if (!routeData) {
        routeData = {
            path: '/',
            name: 'home',
            component: routeMap['Home'],
            children: [
                {
                    path: "/dashboard",
                    name: "dashboard",
                    meta: {
                        title: '系统首页'
                    },
                    component: routeMap['Dashboard']
                },
                {
                    path: '/:catchAll(.*)',
                    name: '404',
                    meta: {
                        title: '找不到页面'
                    },
                    component: routeMap['NotFound']
                },
                {
                    path: '/403',
                    name: '403',
                    meta: {
                        title: '没有权限'
                    },
                    component: routeMap['NoAuth']
                }
            ]
        };
    }
    routes.length && routes.forEach(route => {
        if (route.path) {
            routeData.children.push({
                path: route.path.indexOf('?') > -1  ? route.path.split('?')[0] : route.path,
                name: route.name,
                component: () => import('../' + route.component),
                meta: {
                    title: route.title
                },
            })
        }
        if (route.children && route.children.length) {
            formatRoutes(route.children, routeData);
        }
    });
    return routeData;
};

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    saveRefreshTime()
    document.title = `${to.meta.title} | 光学IoT平台`;

    if(to.meta.openView){
        document.title = to.meta.title
        next();
        return
    }
    if (!getAccessToken() && to.path !== '/login') {
        next('/login');
    } else if ((isFetchRemote || !getMenuItems()) && to.path !== '/login') {
        getMenuByUsername(getUsername()).then((response) => {
            const responseData = response.data
            if (responseData.code === '000000') {
                isFetchRemote = false;
                const menuData = responseData.data;
                setMenuInfo(menuData);
                const routesData = formatRoutes(menuData);
                const menuItems = formatMenus(menuData);
                setMenuItems(menuItems);
                router.addRoute(routesData)
                router.push({
                    path: to.path,
                    query: to.query
                });
            } else {
                isFetchRemote = true;
            }
            next();
        })
            .catch(err => {
                console.log(err);
            });

    } else {
        next();
    }
});

export default router;
