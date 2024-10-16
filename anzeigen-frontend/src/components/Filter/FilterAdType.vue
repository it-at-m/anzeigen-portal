<template>
  <card>
    <template #title>Nach Art filtern</template>
    <template #text>
      <v-checkbox
        v-model="isOffer"
        label="Suche"
        hide-details
        density="compact"
        color="accent"
      />
      <v-checkbox
        v-model="isSeek"
        label="Biete"
        hide-details
        density="compact"
        color="accent"
      />
    </template>
  </card>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";

import Card from "@/components/common/Card.vue";

const isOffer = ref<boolean>(true);
const isSeek = ref<boolean>(true);

watch([isOffer, isSeek], ([newIsOffer, newIsSeek], [oldIsOffer, oldIsSeek]) => {
  if (!newIsOffer && !oldIsSeek) {
    isSeek.value = true;
  } else if (!newIsSeek && !oldIsOffer) {
    isOffer.value = true;
  }
});
</script>

<style scoped></style>
