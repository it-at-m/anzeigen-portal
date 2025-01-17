<template>
  <div class="w-md-66 w-sm-75">
    <v-file-upload
      v-if="!computedPicture"
      :disabled="disabled"
      title="Optionales Titelbild hochladen"
      density="compact"
      @update:model-value="uploadPicture"
    />
    <v-file-upload-item
      v-else
      :disabled="disabled"
      title="Ein Titelbild wurde ausgewählt."
      clearable
      @click:remove="clearFile"
    >
      <template #prepend>
        <v-icon icon="mdi-panorama-outline" />
      </template>
    </v-file-upload-item>
  </div>
</template>

<script setup lang="ts">
import type { SwbImageTO } from "@/api/swbrett";

import { computed } from "vue";
import { VFileUpload, VFileUploadItem } from "vuetify/labs/VFileUpload";

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

  return new File([new Blob([""])], "Titelbild", {
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
