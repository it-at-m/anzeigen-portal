// Composables
import { createRouter, createWebHashHistory } from "vue-router";

import { ROUTES_BOARD } from "@/Constants";
import Board from "@/views/AdBoard.vue";

const routes = [
  {
    path: "/",
    name: ROUTES_BOARD,
    component: Board,
    meta: {},
  },
  { path: "/:catchAll(.*)*", redirect: "/" }, // CatchAll route
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
