<template>
  <v-checkbox
    v-model="isChecked"
    color="accent"
    :disabled="disabled"
    :rules="[(value) => value || 'Bitte akzeptieren Sie die AGB.']"
  >
    <template #label>
      <p>
        Ich bin mit den
        <a @click="clickedFileDownload('AGB_FILE')">
          Nutzungsbedingungen (AGB)
        </a>
        einverstanden. Das Schwarze Brett darf nur zu rein privaten Zwecken
        genutzt werden. Insbesondere Werbung kommerzieller Art ist nicht
        gestattet! Informationen zur Verarbeitung personenbezogener Daten
        enthalten die
        <a @click="clickedFileDownload('DATENSCHUTZHINWEISE_FILE')">
          Datenschutzhinweise
        </a>
        .
      </p>
    </template>
  </v-checkbox>
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { useDownloadFile } from "@/composables/useDownloadFile.ts";
import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();
const downloadFile = useDownloadFile();

/**
 * Value if checkbox is checked
 */
const isChecked = defineModel<boolean>({ default: false });

/**
 * Disables the checkbox input
 */
defineProps<{
  disabled?: boolean;
}>();

const clickedFileDownload = (settingName: SettingTOSettingNameEnum) => {
  const setting = settingStore.getSetting(settingName);
  if (setting?.fileValue?.id) {
    downloadFile(setting?.fileValue?.id);
  }
};
</script>
