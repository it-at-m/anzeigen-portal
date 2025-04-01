<template>
  <v-select
    prepend-icon="mdi-form-select"
    :model-value="computedSetting?.textValue"
    :label="settingName"
    hide-details
    variant="outlined"
    density="compact"
    :items="options.value"
    @update:model-value="updatedValue"
  >
    <template #item="{ item, props }">
      <v-list-item
        v-bind="props"
        :title="t(`generalSettings.choices.${options.key}.${item.value}`)"
      />
    </template>
    <template #selection="{ item }">
      {{ t(`generalSettings.choices.${options.key}.${item.value}`) }}
    </template>
  </v-select>
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import { useSettingStore } from "@/stores/settings.ts";

const { t } = useI18n();

const CRITERIA_VALUES = ["title", "price", "creationDateTime"];

const SORTING_VALUES = ["asc", "desc"];

const settingStore = useSettingStore();

const { settingName } = defineProps<{
  settingName: SettingTOSettingNameEnum;
}>();

const options = computed(() =>
  settingName === "DEFAULT_ORDERING"
    ? { value: SORTING_VALUES, key: "ordering" }
    : { value: CRITERIA_VALUES, key: "sorting" }
);

const computedSetting = computed(() => settingStore.getSetting(settingName));

const updatedValue = (value: string) => {
  settingStore.setSetting({ ...computedSetting.value, textValue: value });
};
</script>

<style scoped></style>
