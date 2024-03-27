import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

import {Menu} from "./menu";
import HomeView from "../views/common/Home.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
    children: Menu
  }, {
    path: "/login",
    name: "Login",
    meta: {
      title: '登录'
    },
    component: () => import ("../views/common/Login.vue")
  }, {
    path: '/403',
    name: '403',
    meta: {
      title: '没有权限'
    },
    component: () => import ('../views/common/403.vue')
  },{
    path: '/:cathchAll(.*)',
    name: '404',

    component: () => import('../views/common/404.vue')
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(async(to, from, next) => {
  const hasToken = localStorage.getItem("token");
  if (hasToken || to.path === '/login') {
    next();
  }
  else{
    next('/login');
  }

})



export default router

