<template>
  <v-file-input
    label="Anhänge hochladen"
    :model-value="computedFiles"
    :disabled="disabled"
    class="mb-4"
    multiple
    :accept="ALLOWED_FILE_TYPES"
    hide-details
    :clearable="false"
    variant="outlined"
    density="compact"
    @update:model-value="uploadedFile"
  >
    <template #selection>Weitere Anhänge hochladen</template>
  </v-file-input>
  <div
    class="pl-10"
    :class="{ 'mb-4': computedFiles.length > 0 }"
  >
    <v-file-upload-item
      v-for="file in computedFiles"
      :key="file.name"
      :file="file"
      :disabled="disabled"
      :file-icon="computedIcon(file)"
      clearable
      density="compact"
      show-size
      @click:remove="removeFile(file)"
    />
  </div>
</template>

<script setup lang="ts">
import type { SwbFileTO } from "@/api/swbrett";

import { computed } from "vue";
import { VFileUploadItem } from "vuetify/labs/VFileUpload";

import { Levels } from "@/api/error.ts";
import { useSnackbar } from "@/composables/useSnackbar.ts";
import {
  ALLOWED_FILE_TYPES,
  FILE_SIZE_TO_BIG,
  TOO_MANY_FILES,
} from "@/Constants.ts";
import { useSettingStore } from "@/stores/settings.ts";

const settingStore = useSettingStore();
const snackbar = useSnackbar();

const maxFileSize =
  settingStore.getSetting("MAX_SWB_FILE_SIZE")?.numberValue || 1;
const maxFileNumber =
  settingStore.getSetting("MAX_SWB_FILES_LENGTH")?.numberValue || 5;

const { modelValue } = defineProps<{
  modelValue?: SwbFileTO[];
  disabled?: boolean;
}>();

const emit = defineEmits<{
  "update:modelValue": [swbFiles: SwbFileTO[]];
}>();

const computedFiles = computed(() => {
  if (!modelValue || modelValue.length === 0) {
    return [];
  }

  return modelValue.map((swbFile) => {
    // fuck the backend - imageBase64 will always be 0 length
    return new File(
      [new Uint8Array(swbFile.size || 0)],
      swbFile.name || "undefined",
      {
        type: "application/octet-stream",
      }
    );
  });
});

const removeFile = (removeFile: File) => {
  if (!modelValue) {
    return;
  }

  emit(
    "update:modelValue",
    modelValue.filter((file) => file.name !== removeFile.name)
  );
};

const computedIcon = (file: File) =>
  file.name.endsWith("pdf")
    ? "mdi-file-document-outline"
    : "mdi-panorama-outline";

const uploadedFile = async (uploads: File[] | File) => {
  const toReturn: SwbFileTO[] = modelValue || [];

  if (!Array.isArray(uploads)) {
    uploads = [uploads];
  }

  if (uploads.length + toReturn.length > maxFileNumber) {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: TOO_MANY_FILES(maxFileSize.toString()),
    });
    return;
  }

  const aggregatedFileSize = uploads.reduce((acc, file) => acc + file.size, 0);
  if (aggregatedFileSize > maxFileSize * 1024 * 1024) {
    snackbar.sendMessage({
      level: Levels.WARNING,
      message: FILE_SIZE_TO_BIG(maxFileSize.toString()),
    });
    return;
  }

  for (const file of uploads) {
    if (
      toReturn.some(
        (swbFile) => swbFile.name === file.name && swbFile.size === file.size
      )
    ) {
      continue;
    }

    const reader = new FileReader();
    reader.onloadend = function () {
      const result = (reader.result as string).split(",")[1];

      toReturn.push({
        size: file.size,
        name: file.name,
        fileBase64: window.btoa(result),
      });
    };

    reader.readAsDataURL(file);
  }

  emit("update:modelValue", toReturn);
};
</script>

<style scoped></style>
