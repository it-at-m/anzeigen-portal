import type { RouteLocationNormalizedGeneric } from "vue-router";

import { createRouter, createWebHashHistory } from "vue-router";

import {
  ROUTES_AD,
  ROUTES_ADMIN,
  ROUTES_BOARD,
  ROUTES_MYBOARD,
} from "@/Constants";
import pinia from "@/plugins/pinia";
import { useUserStore } from "@/stores/user.ts";
import AdBoard from "@/views/AdBoard.vue";
import AdDetailsView from "@/views/AdDetailsView.vue";
import AdminBoard from "@/views/AdminBoard.vue";

const routes = [
  {
    path: "/board",
    name: ROUTES_BOARD,
    component: AdBoard,
    meta: {},
  },
  {
    path: "/myboard",
    name: ROUTES_MYBOARD,
    component: AdBoard,
    meta: {},
  },
  {
    path: "/ad",
    name: ROUTES_AD,
    component: AdDetailsView,
    meta: {},
  },
  {
    path: "/admin",
    name: ROUTES_ADMIN,
    component: AdminBoard,
    meta: {},
  },
  {
    path: "/:catchAll(.*)*",
    redirect: "/board",
    beforeEnter: (to: RouteLocationNormalizedGeneric) => {
      if (!to.query.order || !to.query.sortBy) {
        to.query.order = "asc";
        to.query.sortBy = "title";
      }
    },
  }, // CatchAll route
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    const scrollPositionTop = savedPosition ? savedPosition?.top : 0;

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({ left: 0, top: scrollPositionTop });
      }, 500);
    });
  },
});

/**
 * Admin navigation guard
 */
router.beforeEach((to) => {
  const userStore = useUserStore(pinia);
  if (to.name === ROUTES_ADMIN && !userStore.isAdmin)
    return { name: ROUTES_BOARD };
});

export default router;
