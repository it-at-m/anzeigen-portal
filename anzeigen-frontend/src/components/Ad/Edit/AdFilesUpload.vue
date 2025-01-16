<template>
  <div class="w-md-66 w-sm-75 my-2">
    <v-file-upload
      :model-value="computedFiles"
      clearable
      density="compact"
      multiple
      title="Optionale Anhänge hochladen"
      show-size
      @update:model-value="uploadedFile"
    >
      <template #item="{ file }">
        <v-file-upload-item
          density="compact"
          :file="file"
          @click:remove="removeFile(file)"
        />
      </template>
    </v-file-upload>
  </div>
</template>

<script setup lang="ts">
import type { SwbFileTO } from "@/api/swbrett";

import { computed } from "vue";
import { VFileUpload, VFileUploadItem } from "vuetify/labs/VFileUpload";

const { modelValue } = defineProps<{
  modelValue?: SwbFileTO[];
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

const uploadedFile = async (uploads: File[]) => {
  const toReturn: SwbFileTO[] = modelValue || [];

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
