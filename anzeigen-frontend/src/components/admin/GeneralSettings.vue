<template>
  <ad-display-card>
    <template #title>Allgemeine Einstellungen</template>
    <template #text>
      <v-text-field
        v-for="setting in settingStore.settings"
        :key="setting.id"
        :label="setting.settingName"
        :model-value="
          setting.numberValue ||
          setting.textValue ||
          setting.flagValue ||
          setting.fileValue
        "
        variant="outlined"
        color="accent"
      />
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { SettingTO } from "@/api/swbrett";

import { onMounted } from "vue";

import { Levels } from "@/api/error.ts";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useGetSettings } from "@/composables/api/useSettingsApi.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { API_ERROR_MSG } from "@/Constants.ts";
import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();

const snackbar = useSnackbar();

const {
  call: getSettings,
  data: settingsData,
  loading: settingsLoading,
  error: settingsError,
} = useGetSettings();

onMounted(async () => {
  if (!settingStore.isLoaded) {
    await getSettings();

    if (settingsError.value) {
      snackbar.sendMessage({
        level: Levels.ERROR,
        message: API_ERROR_MSG,
      });
      return;
    }

    settingStore.setSettings(settingsData.value as SettingTO[]);
  }
});

/**
 * Loads settings and stores them in the settings store.
 */
const loadSettings = async () => {};
</script>

<style scoped></style>
