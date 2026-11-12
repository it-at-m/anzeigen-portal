<template>
  <ad-chip
    :text="views.toString()"
    :icon="computedIcon"
  />
</template>

<script setup lang="ts">
import { mdiEyeOffOutline, mdiEyeOutline } from "@mdi/js";
import { computed } from "vue";

import AdChip from "@/components/common/AdChip.vue";

const { views = 0 } = defineProps<{
  views?: number;
}>();

/**
 * Computes the icon based on the number of views.
 * If views are 0 or less, it shows a "closed eye" icon, otherwise it shows a regular "eye" icon.
 */
const computedIcon = computed(() =>
  views <= 0 ? mdiEyeOffOutline : mdiEyeOutline
);
</script>

<style scoped></style>
