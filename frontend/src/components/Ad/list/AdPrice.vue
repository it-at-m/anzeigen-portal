<template>
  <p
    v-if="toGiveAway"
    class="text-h5 font-weight-bold"
  >
    {{ t("ad.priceOptions.toGiveAway") }}
  </p>
  <p
    v-else
    class="text-h5 font-weight-bold"
  >
    {{ Math.abs(price) }}€ {{ appendixTag }}
  </p>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

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
const appendixTag = computed(() =>
  price < 0 ? t("ad.priceOptions.negotiation", 0) : ""
);
</script>

<style scoped></style>
