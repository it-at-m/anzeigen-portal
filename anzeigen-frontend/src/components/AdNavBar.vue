<template>
  <account-card :loading="loading" />
  <ad-edit-button
    class="w-100 mb-4"
    size="large"
    flat
    @click="triggerDialog"
  />
  <ad-display-sheet class="mb-4">
    <filter-ad-category />
    <filter-ad-type />
  </ad-display-sheet>

  <sort-ad-selection />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useEventBus } from "@vueuse/core";
import { computed } from "vue";

import { Levels } from "@/api/error";
import AdEditButton from "@/components/Ad/AdEditButton.vue";
import AccountCard from "@/components/common/AccountCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import FilterAdCategory from "@/components/Filter/FilterAdCategory.vue";
import FilterAdType from "@/components/Filter/FilterAdType.vue";
import SortAdSelection from "@/components/Filter/SortAdSelection.vue";
import {
  useCreateUser,
  useFindUser,
  useUserInfo,
} from "@/composables/api/useUserApi";
import { useDialogEventBus } from "@/composables/useEventBus";
import { useSnackbar } from "@/composables/useSnackbar";
import { API_ERROR_MSG } from "@/Constants";
import { useUserStore } from "@/stores/user";

const dialogBus = useDialogEventBus();
const userStore = useUserStore();
const snackbar = useSnackbar();

const {
  call: userInfoCall,
  data: userInfoData,
  error: userInfoError,
  loading: userInfoLoading,
} = useUserInfo();

const {
  call: findUserCall,
  data: findUserData,
  loading: findUserLoading,
} = useFindUser();

const {
  call: createUserCall,
  data: createUserData,
  loading: createUserLoading,
} = useCreateUser();

const triggerDialog = () => {
  dialogBus.emit(undefined);
};

const loading = computed(
  () =>
    userInfoLoading.value || findUserLoading.value || createUserLoading.value
);

const currentUser = computed(() => findUserData.value || createUserData.value);

const loadUser = async () => {
  // userinfo call
  await userInfoCall();
  if (userInfoError.value) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
    return;
  }

  // save into store
  userStore.setUser(JSON.parse(JSON.stringify(userInfoData.value)));

  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  await findUserCall({ body: userStore.lhmObjectId! });

  if (findUserData.value?.id === undefined) {
    // no user was found - therefore create a new one
    await createUserCall({
      swbUserTO: {
        displayName: userStore.getUser?.displayName,
        lhmObjectId: userStore.lhmObjectId,
      },
    });
  }

  snackbar.sendMessage({
    level: findUserData.value?.id ? Levels.INFO : Levels.SUCCESS,
    message: `Willkommen ${findUserData.value?.id ? "" : "zurück"} ${currentUser.value?.displayName}.`,
  });

  userStore.setUserId(currentUser.value?.id || -1);
};

/**
 * Load User in setup block - happens only once
 */
loadUser();
</script>

<style scoped></style>
