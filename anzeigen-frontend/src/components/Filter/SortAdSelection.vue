<template>
  <ad-display-card>
    <template #title>Sortieren</template>
    <template #text>
      <v-select
        v-model="sortingCriteria"
        color="accent"
        hide-details
        :items="sortingOrderSelections"
        variant="outlined"
      />
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import type { CriteriaValue } from "@/types/SortingOrderCriteria";

import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import {
  sortingOrderSelections,
  useIsValidOrderSelection,
} from "@/composables/useGetSortingSelections";

const orderQuery = useRouteQuery("order");
const orderByQuery = useRouteQuery("sortBy");

const sortingCriteria = ref<CriteriaValue>({ criteria: "titel", order: "asc" });

onMounted(() => {
  if (
    orderQuery.value &&
    orderByQuery.value &&
    useIsValidOrderSelection({
      criteria: orderByQuery.value.toString(),
      order: orderQuery.value.toString(),
    })
  ) {
    sortingCriteria.value = {
      criteria: orderByQuery.value,
      order: orderQuery.value,
    } as CriteriaValue;
  }

  orderQuery.value = sortingCriteria.value.order;
  orderByQuery.value = sortingCriteria.value.criteria;
});

watch(sortingCriteria, (newSortingCriteria) => {
  orderQuery.value = newSortingCriteria.order;
  orderByQuery.value = newSortingCriteria.criteria;
});
</script>
