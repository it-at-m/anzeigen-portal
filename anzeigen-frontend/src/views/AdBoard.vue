<template>
  <div>
    <ad-dialog
      v-model="dialog"
      :loading="loading"
      @deactivate-ad="deactivateAdClick"
      @create-ad="createAdClick"
      @update-ad="updateAdClick"
    />
    <v-row>
      <v-col
        lg="3"
        cols="12"
      >
        <ad-nav-bar />
      </v-col>
      <v-col
        lg="9"
        cols="12"
      >
        <v-sheet
          v-if="motd?.textValue"
          class="mb-4 pa-4"
          rounded
          color="accent"
        >
          <p>{{ motd.textValue }}</p>
        </v-sheet>
        <ad-list />
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { computed, ref } from "vue";

import { Levels } from "@/api/error";
import AdDialog from "@/components/AdDialog.vue";
import AdList from "@/components/AdList.vue";
import AdNavBar from "@/components/AdNavBar.vue";
import {
  useCreateAd,
  useDeactivateAd,
  useUpdateAd,
} from "@/composables/api/useAdApi";
import {
  useSnackbarEventBus,
  useUpdateAdListEventBus,
} from "@/composables/useEventBus";
import {
  API_ERROR_MSG,
  CREATE_AD_SUCCESS,
  DELETE_AD_SUCCESS,
  UPDATE_AD_SUCCESS,
} from "@/Constants";
import { useSettingStore } from "@/stores/settings.ts";
import { useUserStore } from "@/stores/user";

const dialog = ref<boolean>(false);

const updateAdListEventBus = useUpdateAdListEventBus();

const snackbarEventBus = useSnackbarEventBus();

const userStore = useUserStore();

const settingStore = useSettingStore();

const {
  loading: deactivateAdLoading,
  call: deactivateAd,
  error: deactivateAdError,
} = useDeactivateAd();

const {
  loading: createAdLoading,
  call: createAd,
  error: createAdError,
} = useCreateAd();

const {
  loading: updateAdLoading,
  call: updateAd,
  error: updateAdError,
} = useUpdateAd();

/**
 * Aggregated isLoading over all api-calls
 */
const loading = computed(
  () =>
    createAdLoading.value || updateAdLoading.value || deactivateAdLoading.value
);

const motd = computed(() => settingStore.getSetting("MOTD"));

/**
 * Aggregated isError over all api-calls
 */
const error = computed(
  () => createAdError.value || updateAdError.value || deactivateAdError.value
);

/**
 * Deactivates an advertisement by its ID and handles the result.
 *
 * @param id - The ID of the advertisement to deactivate.
 */
const deactivateAdClick = async (id: number) => {
  await deactivateAd({ id });
  finalHandler(DELETE_AD_SUCCESS);
};

/**
 * Updates an advertisement with the provided data and handles the result.
 *
 * @param updatedAd - The updated advertisement data.
 */
const updateAdClick = async (updatedAd: AdTO) => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  await updateAd({ adTO: updatedAd, id: updatedAd.id! });
  finalHandler(UPDATE_AD_SUCCESS);
};

/**
 * Creates a new advertisement with the provided data and handles the result.
 *
 * @param createdAd - The new advertisement data.
 */
const createAdClick = async (createdAd: AdTO) => {
  // set user to this user
  createdAd.swbUser = userStore.swbUserTo;

  await createAd({ adTO: createdAd });
  finalHandler(CREATE_AD_SUCCESS);
};

/**
 * Emits a snackbar message and closes the dialog afterward.
 *
 * @param message - The success message to display if no error occurs.
 */
const finalHandler = (message: string) => {
  if (error.value) {
    snackbarEventBus.emit({ message: API_ERROR_MSG, level: Levels.ERROR });
  } else {
    snackbarEventBus.emit({ message, level: Levels.SUCCESS });
    updateAdListEventBus.emit();
    dialog.value = false;
  }
};
</script>

<style scoped></style>
