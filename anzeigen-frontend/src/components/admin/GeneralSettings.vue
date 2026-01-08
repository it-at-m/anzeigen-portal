<template>
  <ad-display-card>
    <template #title>{{ t("generalSettings.generalSettings") }}</template>
    <template #text>
      <ad-display-card>
        <template #subtitle>{{ t("generalSettings.ads") }}</template>
        <template #text>
          <setting-with-description
            v-for="setting in NUMBER_VALUE_SETTINGS"
            :key="setting"
            :setting-name="setting"
          >
            <template #default="settingName">
              <number-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
          <setting-with-description setting-name="AGB_FILE">
            <template #default="settingName">
              <file-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
          <setting-with-description setting-name="DATENSCHUTZHINWEISE_FILE">
            <template #default="settingName">
              <file-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
        </template>
      </ad-display-card>
      <ad-display-card>
        <template #subtitle>{{ t("generalSettings.email") }}</template>
        <template #text>
          <setting-with-description setting-name="DISALLOWED_EMAIL_DOMAINS">
            <template #default="settingName">
              <text-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
          <setting-with-description
            setting-name="DISALLOWED_EMAIL_DOMAINS"
            alt-text-index="switch"
          >
            <template #default="settingName">
              <bool-value-setting
                :setting-name="settingName.settingName"
                :setting-label="settingName.altSettingText"
              />
            </template>
          </setting-with-description>
        </template>
      </ad-display-card>
      <ad-display-card>
        <template #subtitle>{{ t("generalSettings.paging") }}</template>
        <template #text>
          <setting-with-description setting-name="MAX_PAGE_SIZE">
            <template #default="settingName">
              <number-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
          <setting-with-description setting-name="DEFAULT_SORTING">
            <template #default="settingName">
              <text-select-value-setting
                :setting-name="settingName.settingName"
              />
            </template>
          </setting-with-description>
          <setting-with-description setting-name="DEFAULT_ORDERING">
            <template #default="settingName">
              <text-select-value-setting
                :setting-name="settingName.settingName"
              />
            </template>
          </setting-with-description>
        </template>
      </ad-display-card>
      <ad-display-card>
        <template #subtitle>{{ t("generalSettings.system") }}</template>
        <template #text>
          <setting-with-description setting-name="MOTD">
            <template #default="settingName">
              <text-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
          <setting-with-description setting-name="MAX_ARCHIVE_DATE_RANGE">
            <template #default="settingName">
              <number-value-setting :setting-name="settingName.settingName" />
            </template>
          </setting-with-description>
        </template>
      </ad-display-card>
      <div class="mx-4">
        <v-row>
          <v-col class="d-flex justify-end">
            <v-btn
              class="mr-2"
              variant="outlined"
              prepend-icon="mdi-undo-variant"
              :disabled="!settingStore.isDirty || updateSettingsLoading"
              color="accent-darken-1"
              @click="reloadSettings"
            >
              {{ t("generalSettings.resetSettings") }}
            </v-btn>
            <v-btn
              id="save-btn"
              variant="flat"
              :disabled="!settingStore.isDirty"
              prepend-icon="mdi-content-save"
              color="accent"
              :loading="updateSettingsLoading"
              @click="saveSettings"
            >
              {{ t("common.save") }}
            </v-btn>
          </v-col>
        </v-row>
      </div>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { SettingTO, SettingTOSettingNameEnum } from "@/api/swbrett";

import { useI18n } from "vue-i18n";

import { Levels } from "@/api/error.ts";
import BoolValueSetting from "@/components/admin/general/BoolValueSetting.vue";
import FileValueSetting from "@/components/admin/general/FileValueSetting.vue";
import NumberValueSetting from "@/components/admin/general/NumberValueSetting.vue";
import SettingWithDescription from "@/components/admin/general/SettingWithDescription.vue";
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

const { t } = useI18n();

const {
  call: updateSettings,
  loading: updateSettingsLoading,
  data: updateSettingsData,
  error: updateSettingsError,
} = usePutSettings();

const NUMBER_VALUE_SETTINGS: SettingTOSettingNameEnum[] = [
  "MAX_SWB_IMAGE_SIZE",
  "MAX_SWB_FILE_SIZE",
  "MAX_SWB_FILES_LENGTH",
  "MAX_EXPIRY_DATE_RANGE",
  "MAX_RENTAL_DATE_RANGE",
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

<style scoped>
#save-btn.v-btn--disabled.v-btn {
  opacity: 50% !important;
}
</style>
