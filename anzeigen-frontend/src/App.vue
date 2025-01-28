<template>
  <v-app>
    <the-snackbar-queue />
    <v-app-bar color="primary">
      <v-container class="ad-max-width">
        <v-row
          align="center"
          justify="center"
        >
          <v-col>
            <v-row
              align="center"
              justify="space-between"
            >
              <v-col
                cols="3"
                class="d-flex align-center justify-start"
              >
                <router-link
                  :to="{
                    name: ROUTES_BOARD,
                    query: DEFAULT_BOARD_QUERIES,
                  }"
                >
                  <v-toolbar-title class="font-weight-bold">
                    <span class="text-white">Schwarzes </span>
                    <span class="text-secondary">Brett</span>
                  </v-toolbar-title>
                </router-link>
              </v-col>
              <v-col
                cols="6"
                class="d-flex align-center justify-center"
              >
                <search-ad />
              </v-col>
              <v-col cols="2">
                <router-link
                  v-if="userStore.isAdmin"
                  :to="{ name: ROUTES_ADMIN }"
                >
                  <v-toolbar-title class="font-weight-bold d-flex justify-end">
                    <span class="text-white">Admin</span>
                    <span class="text-secondary">Board</span>
                  </v-toolbar-title>
                </router-link>
                <v-spacer v-else />
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
    <v-main>
      <v-container class="ad-max-width">
        <v-row>
          <v-col>
            <router-view v-slot="{ Component }">
              <v-fade-transition mode="out-in">
                <component :is="Component" />
              </v-fade-transition>
            </router-view>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import type { AdCategory, SettingTO } from "@/api/swbrett";

import { useTitle } from "@vueuse/core";
import { computed, onMounted } from "vue";

import { Levels } from "@/api/error.ts";
import SearchAd from "@/components/Filter/SearchAd.vue";
import TheSnackbarQueue from "@/components/TheSnackbarQueue.vue";
import { useGetCategories } from "@/composables/api/useCategoriesApi.ts";
import { useGetSettings } from "@/composables/api/useSettingsApi.ts";
import {
  useCreateUser,
  useFindUser,
  useUserInfo,
} from "@/composables/api/useUserApi.ts";
import { useUpdateCategories } from "@/composables/updateCategories.ts";
import { useApi } from "@/composables/useApi";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import {
  API_ERROR_MSG,
  DEFAULT_BOARD_QUERIES,
  ROUTES_ADMIN,
  ROUTES_BOARD,
} from "@/Constants";
import { useCategoriesStore } from "@/stores/adcategory.ts";
import { useSettingStore } from "@/stores/settings.ts";
import { useUserStore } from "@/stores/user.ts";

useApi();

useTitle("Anzeigen Portal");

const updateCategories = useUpdateCategories();

const settingStore = useSettingStore();
const userStore = useUserStore();
const snackbar = useSnackbar();
const categoriesStore = useCategoriesStore();

const {
  call: getSettings,
  data: settingsData,
  error: settingsError,
} = useGetSettings();

const {
  call: userInfoCall,
  data: userInfoData,
  error: userInfoError,
} = useUserInfo();

const { call: findUserCall, data: findUserData } = useFindUser();

const { call: createUserCall, data: createUserData } = useCreateUser();

const {
  call: getCategories,
  data: categoryData,
  error: categoryError,
} = useGetCategories();

const currentUser = computed(() => findUserData.value || createUserData.value);

/**
 * Initialize the stores
 */
onMounted(async () => {
  if (!settingStore.isLoaded) {
    await loadSettings();
  }
  if (!userStore.userID) {
    await loadUser();
  }
  if (categoriesStore.isEmpty) {
    await updateCategories();
  }
});

/**
 * Loads settings and stores them in the settings store.
 */
const loadSettings = async () => {
  await getSettings();

  if (settingsError.value) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
    return;
  }

  settingStore.setSettings(settingsData.value as SettingTO[]);
};

/**
 * Loads current user. Therefore, requests all parameters from the sso endpoint and matches those with the backend.
 * If no user exists a new one will be created.
 */
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

<style>
.ad-max-width {
  max-width: 2000px !important;
}
</style>
