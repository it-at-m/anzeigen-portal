<template>
  <v-select
    prepend-icon="mdi-form-select"
    :model-value="computedSetting?.textValue"
    :label="settingName"
    hide-details
    variant="outlined"
    density="compact"
    :items="options"
    item-title="value"
    item-value="key"
    @update:model-value="updatedValue"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import { useSettingStore } from "@/stores/settings.ts";

const { t } = useI18n();

const CRITERIA_VALUES = [
  { key: "title", value: t("generalSettings.choices.sorting.title") },
  { key: "price", value: t("generalSettings.choices.sorting.price") },
  {
    key: "creationDateTime",
    value: t("generalSettings.choices.sorting.creationDateTime"),
  },
];
const SORTING_VALUES = [
  { key: "asc", value: t("generalSettings.choices.ordering.asc") },
  { key: "desc", value: t("generalSettings.choices.ordering.desc") },
];

const settingStore = useSettingStore();

const { settingName } = defineProps<{
  settingName: SettingTOSettingNameEnum;
}>();

const options = computed(() =>
  settingName === "DEFAULT_ORDERING" ? SORTING_VALUES : CRITERIA_VALUES
);

const computedSetting = computed(() => settingStore.getSetting(settingName));

const updatedValue = (value: string) => {
  settingStore.setSetting({ ...computedSetting.value, textValue: value });
};
</script>

<style scoped></style>
