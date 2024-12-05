<template>
  <account-card />
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
import { onMounted } from "vue";

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
import { useSnackbar } from "@/composables/useSnackbar";
import { API_ERROR_MSG, EV_EDIT_AD_DIALOG } from "@/Constants";
import { useUserStore } from "@/stores/user";

const dialogBus = useEventBus<AdTO>(EV_EDIT_AD_DIALOG);
const userStore = useUserStore();
const snackbar = useSnackbar();

const {
  call: userInfoCall,
  data: userInfoData,
  error: userInfoError,
} = useUserInfo();

const {
  call: findUserCall,
  data: findUserData,
  error: findeUserError,
} = useFindUser();

const {
  call: createUserCall,
  data: createUserData,
  error: createUserError,
} = useCreateUser();

const triggerDialog = () => {
  dialogBus.emit(undefined);
};

onMounted(async () => {
  await loadUser();
});

const loadUser = async () => {
  // userinfo call
  await userInfoCall();
  // save into store
  if (userInfoError.value || !userStore.lhmObjectId) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
  }

  userStore.setUser(JSON.parse(JSON.stringify(userInfoData.value)));

  console.log("lhm object id: ", userInfoData.value.lhmObjectID);

  // findUser request
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  await findUserCall({ body: userStore.lhmObjectId! });

  if (findeUserError.value && userStore.getUser) {
    console.log("Error occured upon finding user");
    await createUserCall({
      swbUserTO: userStore.getUser,
    });

    console.log("Create User data:", createUserData.value);
  } else {
    console.log("findUserData: ", findUserData.value);
  }

  // optional create user
  // save id to store
};
</script>

<style scoped></style>
