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
  </ad-display-sheet>

  <sort-ad-selection />
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";

import { Levels } from "@/api/error";
import { AdTOFromJSONTyped, AdTOToJSONTyped } from "@/api/swbrett";
import AdEditButton from "@/components/Ad/AdEditButton.vue";
import AccountCard from "@/components/common/AccountCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import FilterAdCategory from "@/components/Filter/FilterAdCategory.vue";
import FilterAdType from "@/components/Filter/FilterAdType.vue";
import SortAdSelection from "@/components/Filter/SortAdSelection.vue";
import UserFilter from "@/components/Filter/UserFilter.vue";
import {
  useCreateUser,
  useFindUser,
  useUserInfo,
} from "@/composables/api/useUserApi";
import { useDialogEventBus } from "@/composables/useEventBus";
import { useSnackbar } from "@/composables/useSnackbar";
import {
  API_ERROR_MSG,
  DEFAULT_BOARD_QUERIES,
  EMPTY_ADTO_OBJECT,
  QUERY_NAME_USERID,
  ROUTES_BOARD,
  ROUTES_MYBOARD,
} from "@/Constants";
import router from "@/plugins/router";
import { useUserStore } from "@/stores/user";

const dialogBus = useDialogEventBus();
const userStore = useUserStore();
const snackbar = useSnackbar();

const userQuery = useRouteQuery(QUERY_NAME_USERID);

const route = useRoute();

const isMyBoard = computed(() => route.name === ROUTES_MYBOARD);

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
  dialogBus.emit(AdTOFromJSONTyped(AdTOToJSONTyped(EMPTY_ADTO_OBJECT), false));
};

const loading = computed(
  () =>
    userInfoLoading.value || findUserLoading.value || createUserLoading.value
);

const currentUser = computed(() => findUserData.value || createUserData.value);

const isUserSelected = computed(
  () => userQuery.value && userQuery.value.length !== 0
);

const resetUserQuery = () => {
  if (isMyBoard.value) {
    router.push({
      name: ROUTES_BOARD,
      query: DEFAULT_BOARD_QUERIES,
    });
  } else {
    userQuery.value = null;
  }
};

onMounted(() => {
  if (!userStore.userID) {
    loadUser();
  }
});

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
</script>

<style scoped></style>
