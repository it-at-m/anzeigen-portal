<template>
  <v-file-input
    label="Anhänge hochladen"
    :model-value="computedFiles"
    :disabled="disabled"
    class="mb-4"
    multiple
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
