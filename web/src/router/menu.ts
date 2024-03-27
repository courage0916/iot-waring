export const Menu = [
  {
    path: "/user",
    name: "账号管理",
    component: () => import ("../views/user/index.vue")
  },
  {
    path: "/device",
    name: "设备管理",
    component: () => import ("../views/device/index.vue")
  },
  {
    path: "/setting",
    name: "通知设置",
    component: () => import ("../views/user/setting/index.vue")
  }
]