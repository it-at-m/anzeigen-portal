<template>
  <v-row>
    <v-col
      cols="12"
      md="4"
      align-self="center"
    >
      <v-tooltip
        location="top"
        :text="t(`applicationSettings.${computedSettingName}.tooltip`)"
      >
        <template #activator="{ props }">
          <p v-bind="props">
            {{ t(`applicationSettings.${computedSettingName}.description`) }}
          </p>
        </template>
      </v-tooltip>
    </v-col>
    <v-col
      cols="12"
      md="8"
    >
      <slot
        :setting-name="settingName"
        :alt-setting-text="t(`applicationSettings.${computedSettingName}.text`)"
      />
    </v-col>
  </v-row>
  <v-divider class="my-2" />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const { settingName, altTextIndex } = defineProps<{
  settingName: SettingTOSettingNameEnum;

  /**
   * Uses Index inside the defined settingName e.g. SETTINGNAME.ALTERNATIVE.tooltip
   */
  altTextIndex?: string;
}>();

const computedSettingName = computed(() =>
  altTextIndex ? settingName + "." + altTextIndex : settingName
);
</script>

<style scoped></style>
