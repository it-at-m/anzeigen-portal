<template>
  <v-number-input
    data-test="number-value-setting-input"
    :prepend-icon="mdiNumeric"
    variant="outlined"
    hide-details="auto"
    density="compact"
    :model-value="computedSetting?.numberValue"
    color="accent"
    :label="settingName"
    @update:model-value="updatedValue"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/apis/generated/anzeigen-backend";

import { mdiNumeric } from "@mdi/js";
import { computed } from "vue";

import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();

const { settingName } = defineProps<{
  settingName: SettingTOSettingNameEnum;
}>();

const computedSetting = computed(() => settingStore.getSetting(settingName));

const updatedValue = (value: number) => {
  settingStore.setSetting({ ...computedSetting.value, numberValue: value });
};
</script>

<style scoped></style>
