<template>
  <v-chip
    variant="tonal"
    prepend-icon="mdi-card-multiple"
    color="accent"
    :text="category.name"
  />
</template>

<script setup lang="ts">
import type { AdCategory } from "@/api/swbrett";

defineProps<{
  category: AdCategory;
}>();
</script>

<style scoped></style>
