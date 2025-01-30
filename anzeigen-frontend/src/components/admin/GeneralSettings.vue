<template>
  <ad-display-card>
    <template #title>Allgemeine Einstellungen</template>
    <template #text>
      <v-row>
        <v-col>
          <text-value-setting setting-name="MOTD" />
        </v-col>
      </v-row>
      <v-row>
        <v-col
          cols="12"
          md="6"
        >
          <text-select-value-setting setting-name="DEFAULT_SORTING" />
        </v-col>
        <v-col
          cols="12"
          md="6"
        >
          <text-select-value-setting setting-name="DEFAULT_ORDERING" />
        </v-col>
      </v-row>

      <v-row>
        <v-col
          v-for="settingName in NUMBER_VALUE_SETTINGS"
          :key="settingName"
          cols="12"
          md="6"
          lg="4"
        >
          <number-value-setting :setting-name="settingName" />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <file-value-setting setting-name="AGB_FILE" />
          <file-value-setting setting-name="DATENSCHUTZHINWEISE_FILE" />
        </v-col>
      </v-row>
      <v-row>
        <v-col class="d-flex justify-end">
          <v-btn
            class="mr-2"
            variant="outlined"
            prepend-icon="mdi-undo-variant"
            :disabled="!settingStore.isDirty || updateSettingsLoading"
            color="accent"
            @click="reloadSettings"
          >
            Änderungen verwerfen
          </v-btn>
          <v-btn
            variant="flat"
            :disabled="!settingStore.isDirty"
            prepend-icon="mdi-content-save"
            color="accent"
            :loading="updateSettingsLoading"
            @click="saveSettings"
          >
            Speichern
          </v-btn>
        </v-col>
      </v-row>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { SettingTO, SettingTOSettingNameEnum } from "@/api/swbrett";

import { Levels } from "@/api/error.ts";
import FileValueSetting from "@/components/admin/general/FileValueSetting.vue";
import NumberValueSetting from "@/components/admin/general/NumberValueSetting.vue";
import TextSelectValueSetting from "@/components/admin/general/TextSelectValueSetting.vue";
import TextValueSetting from "@/components/admin/general/TextValueSetting.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { usePutSettings } from "@/composables/api/useSettingsApi.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { useUpdateSettings } from "@/composables/useUpdateSettings.ts";
import { useSettingStore } from "@/stores/settings.ts";

const reloadSettings = useUpdateSettings();

const settingStore = useSettingStore();

const snackbar = useSnackbar();

const {
  call: updateSettings,
  loading: updateSettingsLoading,
  data: updateSettingsData,
  error: updateSettingsError,
} = usePutSettings();

const NUMBER_VALUE_SETTINGS: SettingTOSettingNameEnum[] = [
  "MAX_SWB_IMAGE_SIZE",
  "MAX_PAGE_SIZE",
  "MAX_SWB_FILE_SIZE",
  "MAX_SWB_FILES_LENGTH",
  "MAX_EXPIRY_DATE_RANGE",
  "MAX_ARCHIVE_DATE_RANGE",
];

const saveSettings = async () => {
  await updateSettings({
    settingTO: settingStore.settings,
  });

  if (updateSettingsError.value) {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: "Fehler beim Speichern der Einstellungen.",
    });
  } else {
    snackbar.sendMessage({
      level: Levels.SUCCESS,
      message: "Einstellungen wurden erfolgreich gespeichert.",
    });
  }

  settingStore.setSettings(updateSettingsData.value as SettingTO[]);
};
</script>

<style scoped></style>
