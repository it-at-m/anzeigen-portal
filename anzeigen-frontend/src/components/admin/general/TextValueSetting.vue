<template>
  <v-text-field
    prepend-icon="mdi-text"
    density="compact"
    hide-details="auto"
    variant="outlined"
    :model-value="computedSetting?.textValue"
    color="accent"
    :label="settingName"
    @update:model-value="updatedValue"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { useDebounceFn } from "@vueuse/core";
import { computed } from "vue";

import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();

const { settingName } = defineProps<{
  settingName: SettingTOSettingNameEnum;
}>();

const computedSetting = computed(() => settingStore.getSetting(settingName));

const updatedValue = useDebounceFn((value: string) => {
  settingStore.setSetting({ ...computedSetting.value, textValue: value });
}, 1000);
</script>

<style scoped></style>
