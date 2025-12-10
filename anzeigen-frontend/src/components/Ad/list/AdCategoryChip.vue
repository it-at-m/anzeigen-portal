<template>
  <ad-chip
    :text="category.name"
    icon="mdi-card-multiple-outline"
  />
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

import AdChip from "@/components/common/AdChip.vue";

defineProps<{
  category: AdCategory;
}>();
</script>

<style scoped></style>
