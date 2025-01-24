<template>
  <v-chip
    variant="tonal"
    color="accent"
    :prepend-icon="computedIcon"
    :text="computedText"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";

const { isOffer = false } = defineProps<{
  isOffer?: boolean;
}>();

/**
 * Computes the text based on the offer status.
 */
const computedText = computed(() => (isOffer ? "Biete" : "Suche"));

/**
 * Computes the icon based on the offer status.
 */
const computedIcon = computed(() =>
  isOffer ? "mdi-hand-extended-outline" : "mdi-binoculars"
);
</script>

<style scoped></style>
