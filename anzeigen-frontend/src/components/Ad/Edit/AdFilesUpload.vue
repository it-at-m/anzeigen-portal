<template>
  <v-file-upload
    :model-value="computedFiles"
    clearable
    density="compact"
    multiple
    divider-text="Das ist der Dividertext"
    show-size
    @update:model-value="uploadedFile"
  />
</template>

<script setup lang="ts">
import type { SwbFileTO } from "@/api/swbrett";

import { computed } from "vue";
import { VFileUpload } from "vuetify/labs/VFileUpload";

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
    const data = window.atob(swbFile.fileBase64 || "");
    const blob = new Blob([data]);
    return new File([blob], swbFile.name || "undefined", {
      type: "application/octet-stream",
    });
  });
});

//const computedFiles = () => {
//  if (!modelValue || modelValue.length === 0) {
//    return [];
//  }
//
//  return modelValue.map((swbFile) => {
//    // const data = window.atob(swbFile.fileBase64 || "");
//    const blob = new Blob([]);
//    return new File([blob], swbFile.name || "undefined", {
//      type: "application/octet-stream",
//    });
//  });
//};

const uploadedFile = async (uploads: File[]) => {
  console.log("Called upload File before", uploads.length);
  const toReturn: SwbFileTO[] = modelValue || [];

  for (const file of uploads) {
    if (
      toReturn.some(
        (swbFile) => swbFile.name === file.name && swbFile.size === file.size
      )
    ) {
      continue;
    }
    const base64 = window.btoa(await file.text());
    toReturn.push({
      fileBase64: base64,
      size: file.size,
      name: file.name,
    });
  }

  emit("update:modelValue", toReturn);
};
</script>

<style scoped></style>
