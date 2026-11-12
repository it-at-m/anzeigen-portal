<template>
  <ad-chip
    :text="category.name"
    :icon="mdiCardMultipleOutline"
  />
</template>

<script setup lang="ts">
import type { AdCategory } from "@/apis/generated/anzeigen-backend";

import { mdiCardMultipleOutline } from "@mdi/js";

import AdChip from "@/components/common/AdChip.vue";

defineProps<{
  category: AdCategory;
}>();
</script>

<style scoped></style>
