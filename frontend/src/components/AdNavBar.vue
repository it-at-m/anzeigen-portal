<template>
  <account-card :loading="loading" />
  <ad-edit-button
    class="w-100 mb-4"
    size="large"
    flat
    @click="triggerDialog"
  />
  <ad-display-sheet class="mb-4">
    <user-filter
      v-if="isUserSelected || isMyBoard"
      @click="resetUserQuery"
    />
    <filter-ad-category v-if="!isMyBoard" />
    <filter-ad-type />
    <filter-ad-active v-if="hasAdminRole" />
  </ad-display-sheet>

  <sort-ad-selection />
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { computed } from "vue";
import { useRoute } from "vue-router";

import {
  AdTOFromJSONTyped,
  AdTOToJSONTyped,
} from "@/apis/generated/anzeigen-backend";
import AdEditButton from "@/components/Ad/list/AdEditButton.vue";
import AccountCard from "@/components/common/AccountCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import FilterAdActive from "@/components/filter/FilterAdActive.vue";
import FilterAdCategory from "@/components/filter/FilterAdCategory.vue";
import FilterAdType from "@/components/filter/FilterAdType.vue";
import SortAdSelection from "@/components/filter/SortAdSelection.vue";
import UserFilter from "@/components/filter/UserFilter.vue";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { useDialogEventBus } from "@/composables/useEventBus";
import useHasAnyRole from "@/composables/useHasAnyRole.ts";
import {
  EMPTY_ADTO_OBJECT,
  QUERY_NAME_USERID,
  ROUTES_BOARD,
  ROUTES_MYBOARD,
} from "@/constants.ts";
import router from "@/plugins/router";
import { useUserInfoStore } from "@/stores/userinfo.ts";
import { Role } from "@/types/Role.ts";

const dialogBus = useDialogEventBus();
const userStore = useUserInfoStore();
const defaultQuery = useDefaultQuery();

const userQuery = useRouteQuery(QUERY_NAME_USERID);
const hasAdminRole = useHasAnyRole(Role.FACHADMIN);

const route = useRoute();

// TODO: change maybe to a query param or sth else
const isMyBoard = computed(() => route.path === ROUTES_MYBOARD);

const triggerDialog = () => {
  dialogBus.emit(AdTOFromJSONTyped(AdTOToJSONTyped(EMPTY_ADTO_OBJECT), false));
};

const loading = computed(() => !userStore.userID);

const isUserSelected = computed(
  () => userQuery.value && userQuery.value.length !== 0
);

/**
 * Resets the selection of a specific user - can be the current user (/myboard) or any other.
 */
// TODO does not work correctly
const resetUserQuery = () => {
  console.log("resetuserQuery", isMyBoard.value);
  if (isMyBoard.value) {
    router.push({
      name: ROUTES_BOARD,
      query: defaultQuery.value,
      force: true,
    });
  } else {
    userQuery.value = null;
  }
};
</script>

<style scoped></style>
