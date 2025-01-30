<template>
  <v-file-input
    variant="outlined"
    :model-value="computedFile"
    :label="settingName"
    color="accent"
    density="compact"
    clearable
    @update:model-value="updateFile"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { computed } from "vue";

import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();

const { settingName } = defineProps<{
  settingName: SettingTOSettingNameEnum;
}>();

const computedSetting = computed(() => settingStore.getSetting(settingName));

const computedFile = computed(() => {
  const fileValue = computedSetting?.value?.fileValue;

  if (!fileValue) {
    return undefined;
  }

  return new File(
    [new Uint8Array(fileValue.size || 0)],
    fileValue.name || "undefined",
    {
      type: "application/octet-stream",
    }
  );
});

const updateFile = (files: File | File[] | undefined) => {
  if (!files) {
    settingStore.setSetting({
      ...computedSetting.value,
      fileValue: undefined,
    });
    return;
  }

  const file = Array.isArray(files) ? files[0] : files;

  const reader = new FileReader();
  reader.onloadend = function () {
    const result = (reader.result as string).split(",")[1];

    settingStore.setSetting({
      ...computedSetting.value,
      fileValue: {
        size: file.size,
        name: file.name,
        fileBase64: window.btoa(result),
      },
    });
  };

  reader.readAsDataURL(file);
};
</script>

<style scoped></style>
