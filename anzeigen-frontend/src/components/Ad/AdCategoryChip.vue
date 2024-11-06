<template>
  <v-chip
    variant="tonal"
    prepend-icon="mdi-check-all"
    color="accent"
    :text="category.name"
  />
</template>

<script setup lang="ts">
import type { AdCategory } from "@/types/api/AdCategory";

defineProps<{
  category: AdCategory;
}>();
</script>

<style scoped></style>
