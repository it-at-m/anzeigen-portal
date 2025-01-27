<template>
  <v-chip
    variant="tonal"
    prepend-icon="mdi-check-all"
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
