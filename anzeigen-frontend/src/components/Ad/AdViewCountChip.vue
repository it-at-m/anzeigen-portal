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

const computedIcon = computed(() =>
  views <= 0 ? "mdi-eye-off-outline" : "mdi-eye-outline"
);
</script>

<style scoped></style>
