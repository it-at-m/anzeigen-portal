<template>
  <v-switch
    data-test="bool-value-setting-switch"
    :prepend-icon="mdiTuneVariant"
    color="accent"
    :label="computedSettingText"
    hide-details
    inset
    :model-value="computedSetting.flagValue"
    @update:model-value="updatedValue"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/apis/generated/anzeigen-backend";

import { mdiTuneVariant } from "@mdi/js";
import { computed } from "vue";

import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();

const { settingName, settingLabel = undefined } = defineProps<{
  settingName: SettingTOSettingNameEnum;
  settingLabel?: string;
}>();

const computedSettingText = computed(() =>
  settingLabel ? settingLabel : settingName
);

const computedSetting = computed(() => settingStore.getSetting(settingName));

const updatedValue = (value: boolean | null) => {
  settingStore.setSetting({
    ...computedSetting.value,
    flagValue: value ?? false,
  });
};
</script>

<style scoped></style>
