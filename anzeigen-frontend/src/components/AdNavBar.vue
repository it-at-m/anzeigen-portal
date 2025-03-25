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
    <filter-ad-active v-if="userStore.isAdmin" />
  </ad-display-sheet>

  <sort-ad-selection />
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { computed } from "vue";
import { useRoute } from "vue-router";

import { AdTOFromJSONTyped, AdTOToJSONTyped } from "@/api/swbrett";
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
import {
  EMPTY_ADTO_OBJECT,
  QUERY_NAME_USERID,
  ROUTES_BOARD,
  ROUTES_MYBOARD,
} from "@/Constants";
import router from "@/plugins/router";
import { useUserStore } from "@/stores/user";

const dialogBus = useDialogEventBus();
const userStore = useUserStore();
const defaultQuery = useDefaultQuery();

const userQuery = useRouteQuery(QUERY_NAME_USERID);

const route = useRoute();

const isMyBoard = computed(() => route.name === ROUTES_MYBOARD);

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
const resetUserQuery = () => {
  if (isMyBoard.value) {
    router.push({
      name: ROUTES_BOARD,
      query: defaultQuery.value,
    });
  } else {
    userQuery.value = null;
  }
};
</script>

<style scoped></style>
