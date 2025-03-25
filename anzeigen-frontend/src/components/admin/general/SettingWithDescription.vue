<template>
  <v-row>
    <v-col
      cols="12"
      md="4"
      align-self="center"
    >
      <v-tooltip
        location="top"
        :text="t(`applicationSettings.${settingName}.tooltip`)"
      >
        <template #activator="{ props }">
          <p v-bind="props">
            {{ t(`applicationSettings.${settingName}.description`) }}
          </p>
        </template>
      </v-tooltip>
    </v-col>
    <v-col
      cols="12"
      md="8"
    >
      <slot :setting-name="settingName" />
    </v-col>
  </v-row>
  <v-divider class="my-2" />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { useI18n } from "vue-i18n";

const { t } = useI18n();

defineProps<{
  settingName: SettingTOSettingNameEnum;
}>();
</script>

<style scoped></style>
