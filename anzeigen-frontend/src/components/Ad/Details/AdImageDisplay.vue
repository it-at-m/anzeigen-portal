<template>
  <v-img
    max-height="500"
    class="rounded image-background-color"
    :src="PREVIEW_IMAGE_FILE_URI_PREFIX + computedImage"
  />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { computed, onMounted } from "vue";

import { useGetAdImage } from "@/composables/api/useFilesApi";
import { PREVIEW_IMAGE_FILE_URI_PREFIX } from "@/Constants";

const {
  call: getAdImage,
  data: adImageData,
  loading: adImageLoading,
} = useGetAdImage();

/**
 * Computes the image date to be displayed. This way there is no flickering of the image.
 */
const computedImage = computed(() =>
  adImageLoading.value || !adImageData.value
    ? adDetails.imagePreviewBase64
    : adImageData.value.imageBase64
);

const { adDetails } = defineProps<{
  adDetails: Readonly<AdTO>;
}>();

/**
 * Loads the "big" image upon mounting this component
 */
onMounted(async () => {
  if (adDetails.adImg?.id) {
    await getAdImage({ id: adDetails.adImg?.id });
  }
});
</script>

<style scoped></style>
