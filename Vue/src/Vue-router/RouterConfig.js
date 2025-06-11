import { createRouter, createWebHashHistory } from "vue-router";
const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/user/login",
      name: "login",
      component: () => import("../views/user/UserLogin.vue"),
    },
    {
      path: "/dashboard",
      name: "index",
      component: () => import("../views/LocalHost.vue"),
    },
      {
      path: "/user/register",
      name: "register",
      component: () => import("../views/user/UserRegister.vue"),
    },
    {
      path: "/",
      name: "shop",
      component: () => import("../views/shop/ShopCom.vue"),
    },
      {
      path: "/user/code",
      name: "code",
      component: () => import("../views/user/CodeInv.vue"),
    },
      {
        path: "/shop/change",
        name: "changeShop",
        component: () => import("../views/shop/UpdateShop.vue"),
        props:true
      },
      {
        path: "/shop/insert",
        name: "insertshop",
        component: () => import("../views/shop/InsertShop.vue"),
      },
      {
        path: "/cart",
        name: "cart",
        component: () => import("../views/shop/CartPage.vue"),
      },
      {
        path: "/payment",
        name: "payment",
        component: () => import("../views/payment/LocalPage.vue"),
      },
      {
        path: "/order",
        name: "order",
        component: () => import("../views/shop/OrderPage.vue"),
      },
  ],
});
router.beforeEach((to, from, next) => {
  // 如果要访问的路径不在路由表中，则重定向到指定的链接
  if (to.matched.length === 0) {
    next('/');  // 替换为你想要重定向的链接
  } else {
    next();  // 如果路径在路由表中，正常导航
  }
});
export default router