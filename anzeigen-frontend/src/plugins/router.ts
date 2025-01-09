import type { RouteLocationNormalizedGeneric } from "vue-router";

import { createRouter, createWebHashHistory } from "vue-router";

import { ROUTES_AD, ROUTES_BOARD, ROUTES_MYBOARD } from "@/Constants";
import AdBoard from "@/views/AdBoard.vue";
import AdDetailsView from "@/views/AdDetailsView.vue";

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
  scrollBehavior() {
    return {
      top: 0,
      left: 0,
    };
  },
});

export default router;
