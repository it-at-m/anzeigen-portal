<template>
  <ad-display-card>
    <template #title>Sortieren</template>
    <template #text>
      <v-select
        v-model="sortingCriteria"
        color="accent"
        :items="selections"
        variant="outlined"
      />
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { CriteriaValue, SortingOrder } from "@/types/SortingOrderCriteria";

import { ref } from "vue";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";

const selections = [
  { title: "Titel (alphabetisch)", value: { criteria: "titel", order: "asc" } },
  { title: "Preis aufsteigend", value: { criteria: "price", order: "asc" } },
  { title: "Preis absteigend", value: { criteria: "price", order: "desc" } },
  {
    title: "Erstellungsdatum aufsteigend",
    value: { criteria: "creationDate", order: "asc" },
  },
  {
    title: "Erstellungsdatum absteigend",
    value: { criteria: "creationDate", order: "desc" },
  },
] as SortingOrder[];

const sortingCriteria = ref<CriteriaValue>({ criteria: "titel", order: "asc" });
</script>

<style scoped></style>
