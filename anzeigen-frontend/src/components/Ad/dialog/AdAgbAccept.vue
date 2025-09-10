<template>
  <v-checkbox
    v-model="isChecked"
    color="accent"
    :disabled="disabled"
    :rules="[(value) => value || 'Bitte akzeptieren Sie die AGB.']"
  >
    <template #label>
      <p>
        {{ t("adAgbAccept.agbMessagePart1") }}
        <a
          href="#"
          class="text-decoration-underline link-color"
          @click="clickedFileDownload('AGB_FILE')"
          >{{ t("adAgbAccept.termsOfUse") }}
        </a>
        {{ t("adAgbAccept.agbMessagePart2") }}
        <a
          href="#"
          class="text-decoration-underline link-color"
          @click="clickedFileDownload('DATENSCHUTZHINWEISE_FILE')"
        >
          {{ t("adAgbAccept.dataProtectionInformation") }} </a
        >{{ t("adAgbAccept.agbMessagePart3") }}
      </p>
    </template>
  </v-checkbox>
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { useI18n } from "vue-i18n";

import { useDownloadFile } from "@/composables/useDownloadFile.ts";
import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();
const downloadFile = useDownloadFile();

const { t } = useI18n();

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
  console.debug("test");
  const setting = settingStore.getSetting(settingName);
  if (setting?.fileValue?.id) {
    downloadFile(setting?.fileValue?.id);
    console.debug("test2");
  }
  console.debug("test3");
};
</script>

<style scoped>
.link-color {
  color: #0066cc;
}
</style>
