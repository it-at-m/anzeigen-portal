<template>
  <v-img
    max-height="500"
    class="rounded image-background-color"
    :lazy-src="PREVIEW_IMAGE_FILE_URI_PREFIX + adDetails.imagePreviewBase64"
    :src="PREVIEW_IMAGE_FILE_URI_PREFIX + adImageData?.imageBase64"
  />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { onMounted } from "vue";

import { useGetAdImage } from "@/composables/api/useFilesApi";
import { PREVIEW_IMAGE_FILE_URI_PREFIX } from "@/Constants";

const { call: getAdImage, data: adImageData } = useGetAdImage();

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
