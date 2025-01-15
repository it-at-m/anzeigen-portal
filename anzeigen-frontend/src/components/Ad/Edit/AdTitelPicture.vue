<template>
  <v-file-upload
    v-if="!computedPicture"
    clearable
    density="compact"
    browse-text="Titelbild hochladen"
    title="Titelbild hochladen"
    accept="image/png"
    reverse
    @update:model-value="uploadPicture"
  />
  <v-file-upload-item
    v-else
    :file="computedPicture"
    clearable
    size
    @click:remove="clearFile"
  />
</template>

<script setup lang="ts">
import type { SwbImageTO } from "@/api/swbrett";

import { computed } from "vue";

import AdFilesUpload from "@/components/Ad/Edit/AdFilesUpload.vue";

defineProps<{
  modelValue: SwbImageTO;
}>();

const computedPicture = computed(() => {
  return true;
});

const uploadPicture = (file: File[]) => {};

const clearFile = () => {};
</script>

<style scoped></style>
