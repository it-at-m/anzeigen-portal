<template>
  <v-file-input
    label="Titelbild hochladen"
    variant="outlined"
    density="compact"
    hide-details
    :accept="ALLOWED_IMAGE_TYPES"
    :disabled="disabled"
    prepend-icon="mdi-panorama-outline"
    :model-value="computedPicture"
    @update:model-value="uploadPicture"
    @click:clear="clearFile"
  />
</template>

<script setup lang="ts">
import type { SwbImageTO } from "@/api/swbrett";

import { computed } from "vue";

import { Levels } from "@/api/error.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import { ALLOWED_IMAGE_TYPES, FILE_SIZE_TO_BIG } from "@/Constants.ts";
import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();
const maxImageSize =
  settingStore.getSetting("MAX_SWB_IMAGE_SIZE")?.numberValue || 1;

const snackbar = useSnackbar();

const { modelValue } = defineProps<{
  modelValue: SwbImageTO | undefined;
  disabled?: boolean;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: SwbImageTO | undefined];
}>();

const computedPicture = computed(() => {
  if (!modelValue || (!modelValue.id && !modelValue.imageBase64)) {
    return undefined;
  }

  return new File([new Blob([""])], "Ein Titelbild wurde ausgewählt", {
    type: "image/jpeg",
  });
});

const uploadPicture = async (files: File[] | File) => {
  const file = Array.isArray(files) ? files[0] : files;

  if (!file) {
    return;
  }

  if (file.size > maxImageSize * 1024 * 1024) {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: FILE_SIZE_TO_BIG(maxImageSize.toString()),
    });
    return;
  }

  const reader = new FileReader();
  reader.onloadend = function () {
    const result = (reader.result as string).split(",")[1];
    emit("update:modelValue", {
      imageBase64: result,
    });
  };

  reader.readAsDataURL(file);
};

const clearFile = () => {
  emit("update:modelValue", undefined);
};
</script>

<style scoped></style>
