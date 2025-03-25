<template>
  <v-select
    prepend-icon="mdi-form-select"
    :model-value="computedSetting?.textValue"
    :label="settingName"
    hide-details
    variant="outlined"
    density="compact"
    :items="options"
    @update:model-value="updatedValue"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { computed } from "vue";

import { useSettingStore } from "@/stores/settings.ts";

const CRITERIA_VALUES = ["title", "price", "creationDateTime"];
const SORTING_VALUES = ["asc", "desc"];

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
