<template>
  <v-chip
    variant="flat"
    color="accent"
    :prepend-icon="computedIcon"
  >
    {{ computedText }}</v-chip
  >
</template>

<script setup lang="ts">
import { computed } from "vue";

const { isOffer = false } = defineProps<{
  isOffer?: boolean;
}>();

const computedText = computed(() => (isOffer ? "Biete" : "Suche"));
const computedIcon = computed(() =>
  isOffer ? "mdi-tag-outline" : "mdi-magnify"
);
</script>

<style scoped></style>
