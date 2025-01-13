<template>
  <v-file-upload
    :model-value="modelValue"
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

import { VFileUpload } from "vuetify/labs/VFileUpload";

defineProps<{
  modelValue?: SwbFileTO[];
}>();

const computedFiles = () => {};

const uploadedFile = (upload: File[]) => {
  console.log(upload.length);
};
</script>

<style scoped></style>
