<template>
  <v-file-input
    label="Titelbild hochladen"
    variant="outlined"
    density="compact"
    hide-details
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
