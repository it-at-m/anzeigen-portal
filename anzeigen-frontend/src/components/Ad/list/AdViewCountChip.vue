<template>
  <v-chip
    variant="tonal"
    :prepend-icon="computedIcon"
    color="accent"
    :text="views.toString()"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";

const { views = 0 } = defineProps<{
  views: number;
}>();

/**
 * Computes the icon based on the number of views.
 * If views are 0 or less, it shows a "closed eye" icon, otherwise it shows a regular "eye" icon.
 */
const computedIcon = computed(() =>
  views <= 0 ? "mdi-eye-off-outline" : "mdi-eye-outline"
);
</script>

<style scoped></style>
