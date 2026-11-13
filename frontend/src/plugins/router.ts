import { mdiShieldLock } from "@mdi/js";
import { createRouter, createWebHistory } from "vue-router";
import {
  routes as fileBasedRoutes,
  handleHotUpdate,
} from "vue-router/auto-routes";

import { hasAnyRole } from "@/composables/useHasAnyRole";
import {
  ROUTES_BOARD,
  ROUTES_MYBOARD,
  STATUS_INDICATORS,
} from "@/constants.ts";
import board from "@/routes/board.vue";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserInfoStore } from "@/stores/userinfo";

const routes = [
  ...fileBasedRoutes,
  {
    path: ROUTES_MYBOARD,
    name: "board",
    component: board,
  },
  {
    path: "/:catchAll(.*)*",
    redirect: () => {
      return {
        path: ROUTES_BOARD,
        query: {
          order: "asc",
          sortBy: "title",
        },
      };
    },
  }, // CatchAll route
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach(async (to, from) => {
  const userInfoStore = useUserInfoStore();
  const snackbarStore = useSnackbarStore();
  if (!userInfoStore.userInfo) {
    await userInfoStore.fetchUserInfo();
  }

  if (
    !to.meta.hasAnyRole ||
    hasAnyRole(to.meta.hasAnyRole, userInfoStore.currentRoles)
  ) {
    return true;
  }

  snackbarStore.push({
    color: STATUS_INDICATORS.ERROR,
    text: "Du hast nicht die erforderlichen Berechtigungen, um diese Seite anzuzeigen.",
    icon: mdiShieldLock,
  });

  // Check if application was already running in browser
  if (from.name) {
    return false;
  }

  return { path: "/" };
});

if (import.meta.hot) {
  handleHotUpdate(router);
}

export default router;
