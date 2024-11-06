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

const toGiveAway = computed(() => price === 0);

const appendixTag = computed(() => (price < 0 ? NEGOTIATE_TAG : ""));
</script>

<style scoped></style>
