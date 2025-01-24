<template>
  <p
    v-if="toGiveAway"
    class="text-h5 text-accent font-weight-bold"
  >
    Zu verschenken
  </p>
  <p
    v-else
    class="text-h5 text-accent font-weight-bold"
  >
    {{ Math.abs(price) }}€ {{ appendixTag }}
  </p>
</template>

<script setup lang="ts">
import { computed } from "vue";

const NEGOTIATE_TAG = "VB";

const { price } = defineProps<{
  price: number;
}>();

/**
 * Computes whether the item is being given away for free based on its price.
 */
const toGiveAway = computed(() => price === 0);

/**
 * Computes the appendix tag based on the price, using a negotiate tag for negative prices.
 */
const appendixTag = computed(() => (price < 0 ? NEGOTIATE_TAG : ""));
</script>

<style scoped></style>
