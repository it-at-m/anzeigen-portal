<template>
  <v-app>
    <the-snackbar-queue />
    <no-permison-dialog
      :activator="!hasGeneralPermissions && !!userInfoStore.userInfo"
    />
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
                    query: defaultQuery,
                  }"
                >
                  <v-toolbar-title class="font-weight-bold">
                    <span class="text-white">
                      {{ t(`app.heading`) }}
                    </span>
                  </v-toolbar-title>
                </router-link>
              </v-col>
              <v-col
                cols="6"
                class="d-flex align-center justify-center"
              >
                <search-ad />
              </v-col>
              <v-col cols="auto">
                <div class="w-100 d-flex align-center justify-end">
                  <service-avatar />
                  <v-icon-btn
                    v-if="hasAdminPrivileges"
                    v-tooltip:bottom="
                      route.name === ROUTES_ADMIN
                        ? 'Einstellungen verlassen'
                        : 'Einstellungen öffnen'
                    "
                    size="x-large"
                    class="ml-2 pa-0"
                    :icon="
                      route.name === ROUTES_ADMIN ? mdiArrowULeftTop : mdiCog
                    "
                    variant="plain"
                    @click="
                      route.name === ROUTES_ADMIN
                        ? router.back()
                        : router.push({ name: ROUTES_ADMIN })
                    "
                  />
                </div>
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
import { mdiArrowULeftTop, mdiCog } from "@mdi/js";
import ServiceAvatar from "@variants/components/service-avatar.vue";
import { useTitle } from "@vueuse/core";
import { computed, onBeforeMount, onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";
import { useRoute, useRouter } from "vue-router";

import NoPermisonDialog from "@/components/common/NoPermisonDialog.vue";
import SearchAd from "@/components/filter/SearchAd.vue";
import TheSnackbarQueue from "@/components/TheSnackbarQueue.vue";
import { useGetAppInfo } from "@/composables/api/useSettingsApi.ts";
import { useCreateUser, useFindUser } from "@/composables/api/useUserApi.ts";
import { useUpdateCategories } from "@/composables/updateCategories.ts";
import { useApi } from "@/composables/useApi";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { useSettingsUpdatedEventBus } from "@/composables/useEventBus.ts";
import useHasAnyRole from "@/composables/useHasAnyRole.ts";
import { useUpdateSettings } from "@/composables/useUpdateSettings.ts";
import { ROUTES_ADMIN, ROUTES_BOARD, STATUS_INDICATORS } from "@/constants.ts";
import { useCategoriesStore } from "@/stores/adcategory.ts";
import { useSettingStore } from "@/stores/settings.ts";
import { useSnackbarStore } from "@/stores/snackbar.ts";
import { useUserInfoStore } from "@/stores/userinfo.ts";
import { Role } from "@/types/Role.ts";

useApi();

const { t } = useI18n();

const defaultQuery = useDefaultQuery();
const updateCategories = useUpdateCategories();
const updateSettings = useUpdateSettings();

const settingStore = useSettingStore();
const userInfoStore = useUserInfoStore();
const snackbar = useSnackbarStore();
const categoriesStore = useCategoriesStore();
const settingsUpdatedEventBus = useSettingsUpdatedEventBus();

const hasAdminPrivileges = useHasAnyRole(Role.FACHADMIN);
const hasGeneralPermissions = useHasAnyRole([Role.FACHADMIN, Role.ANWENDER]);

useTitle(t(`app.heading`));
const router = useRouter();
const route = useRoute();

const noPermission = ref<boolean>(false);

const {
  call: findUserCall,
  data: findUserData,
  error: findUserError,
} = useFindUser();

const { call: createUserCall, data: createUserData } = useCreateUser();

const {
  call: appInfoCall,
  data: appInfoData,
  error: appInfoError,
} = useGetAppInfo();

const currentUser = computed(() => findUserData.value || createUserData.value);

/**
 * Initialize the stores
 */
onBeforeMount(async () => {
  if (!settingStore.isLoaded) {
    await updateSettings();

    settingsUpdatedEventBus.emit();
  }
});

onMounted(async () => {
  await appInfoCall();
  if (appInfoError.value || !appInfoData.value?.application.heading) {
    noPermission.value = true;
  }

  if (!userInfoStore.userID) {
    await loadUser();
  }
  if (categoriesStore.isEmpty) {
    await updateCategories();
  }
});

/**
 * Loads current user. Therefore, requests all parameters from the sso endpoint and matches those with the backend.
 * If no user exists a new one will be created.
 */
const loadUser = async () => {
  if (!userInfoStore.userInfo && !userInfoStore.lhmObjectId) {
    // TODO: snackbar error
    return;
  }

  await findUserCall({
    swbUserFind: { lhmObjectID: userInfoStore.lhmObjectId },
  });

  if (findUserData.value?.id === undefined) {
    // no user was found - therefore create a new one
    await createUserCall({
      swbUserTO: {
        displayName: userInfoStore.userInfo?.displayName,
        lhmObjectId: userInfoStore.lhmObjectId,
      },
    });
  }
  if (findUserError.value) {
    // User does not have the proper authorities
    noPermission.value = true;
    snackbar.clear();
    return;
  }

  snackbar.push({
    color: findUserData.value?.id
      ? STATUS_INDICATORS.INFO
      : STATUS_INDICATORS.SUCCESS,
    text: `Willkommen ${findUserData.value?.id ? "zurück" : ""} ${currentUser.value?.displayName || userInfoStore.userInfo?.displayName}.`,
  });

  userInfoStore.setUserId(currentUser.value?.id || -1);
};
</script>

<style>
.ad-max-width {
  max-width: 2000px !important;
}

/* Sanfte Drehung um 90 Grad beim Hovern */
.spin-on-hover {
  transition: transform 0.4s ease-in-out;
}

/* Der v-btn hover-state triggert das innere Icon */
.v-btn:hover .spin-on-hover {
  transform: rotate(90deg);
}
</style>
