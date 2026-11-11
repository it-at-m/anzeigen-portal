<template>
  <v-file-input
    :label="t('adTitlePicture.label')"
    variant="outlined"
    density="compact"
    hide-details="auto"
    :accept="ALLOWED_IMAGE_TYPES"
    :disabled="disabled"
    :loading="sanitizeImgLoading"
    prepend-icon="mdi-panorama-outline"
    color="accent"
    :model-value="computedPicture"
    @update:model-value="uploadPicture"
    @click:clear="clearFile"
  />
</template>

<script setup lang="ts">
import type { SwbImageTO } from "@/apis/generated/anzeigen-backend";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import { useSanitizeImage } from "@/composables/api/useFilesApi.ts";
import {
  ALLOWED_IMAGE_TYPES,
  FILE_SIZE_TO_BIG,
  FILE_TYPE_INCORRECT,
  STATUS_INDICATORS,
} from "@/constants.ts";
import { useSettingStore } from "@/stores/settings.ts";
import { useSnackbarStore } from "@/stores/snackbar.ts";

const { t } = useI18n();

const settingStore = useSettingStore();
const maxImageSize =
  settingStore.getSetting("MAX_SWB_IMAGE_SIZE")?.numberValue || 1;

const snackbar = useSnackbarStore();

const {
  call: sanitizeImg,
  loading: sanitizeImgLoading,
  data: sanitizeImgData,
  error: sanitizeImgError,
} = useSanitizeImage();

const { modelValue } = defineProps<{
  modelValue: SwbImageTO | undefined;
  disabled?: boolean;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: SwbImageTO | undefined];
}>();

/**
 * Computes the picture based on the model value.
 * Returns a new File object if a valid image is available in the model.
 */
const computedPicture = computed(() => {
  if (!modelValue || (!modelValue.id && !modelValue.imageBase64)) {
    return undefined;
  }

  return new File([new Blob([""])], t("adTitlePicture.ruleMsg"), {
    type: "image/jpeg",
  });
});

/**
 * Handles the upload of a picture.
 * Converts the selected file to a base64-encoded string and updates the model value.
 * @param files - The file to upload.
 */
const uploadPicture = async (files: File[] | File) => {
  const file = Array.isArray(files) ? files[0] : files;

  if (!file) {
    return;
  }

  if (file.size > maxImageSize * 1024 * 1024) {
    snackbar.push({
      color: STATUS_INDICATORS.WARNING,
      text: FILE_SIZE_TO_BIG(maxImageSize.toString()),
    });
    return;
  }

  const reader = new FileReader();
  reader.onloadend = async function () {
    const result = (reader.result as string).split(",")[1];

    // Make api call to sanitize and check image
    await sanitizeImg({
      swbImageSanitize: {
        imageBase64: result,
      },
    });

    if (sanitizeImgError.value || !sanitizeImgData.value) {
      snackbar.push({
        color: STATUS_INDICATORS.WARNING,
        text: FILE_TYPE_INCORRECT,
      });
      return;
    }

    emit("update:modelValue", {
      imageBase64: sanitizeImgData.value.toString(),
    });
  };

  reader.readAsDataURL(file);
};

/**
 * Clears the current file by emiting undefined.
 */
const clearFile = () => {
  emit("update:modelValue", undefined);
};
</script>

<style scoped></style>
