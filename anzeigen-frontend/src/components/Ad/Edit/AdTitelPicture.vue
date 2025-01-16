<template>
  <div class="w-md-66 w-sm-75">
    <v-file-upload
      v-if="!computedPicture"
      title="Optionales Titelbild hochladen"
      density="compact"
      @update:model-value="uploadPicture"
    />
    <v-file-upload-item
      v-else
      title="Ein Titelbild wurde ausgewählt."
      clearable
      @click:remove="clearFile"
    >
      <template #prepend>
        <v-icon icon="mdi-panorama-outline" />
      </template>
    </v-file-upload-item>
  </div>
  <v-file-input
    v-if="false"
    :model-value="computedPicture"
    prepend-icon=""
    label="Titelbild"
    @update:model-value="uploadPicture"
  >
    <template #prepend>
      <v-icon
        v-if="!computedPicture"
        icon="mdi-panorama-outline"
      />
      <img
        v-else
        :src="computedPreview"
        alt="Hallo"
        height="50px"
      />
    </template>
  </v-file-input>
</template>

<script setup lang="ts">
import type { SwbImageTO } from "@/api/swbrett";

import { computed } from "vue";
import { VFileUpload, VFileUploadItem } from "vuetify/labs/VFileUpload";

const { modelValue } = defineProps<{
  modelValue: SwbImageTO | undefined;
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

const computedPreview = computed(
  () => "data:image/jpeg;base64," + modelValue?.imageBase64
);

const uploadPicture = async (files: File[] | File) => {
  const file = Array.isArray(files) ? files[0] : files;

  if (!file) {
    return;
  }

  const reader = new FileReader();
  reader.onloadend = function () {
    const result = (reader.result as string).split(",")[1];
    const base64 = window.btoa(result);
    emit("update:modelValue", {
      imageBase64: base64,
    });
  };

  reader.readAsDataURL(file);
};

const clearFile = () => {
  emit("update:modelValue", undefined);
};
</script>

<style scoped></style>
