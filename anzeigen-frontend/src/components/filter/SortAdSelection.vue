<template>
  <ad-display-card>
    <template #title>{{ t("sortAdSelection.title") }}</template>
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
import { useI18n } from "vue-i18n";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { QUERY_NAME_ORDER, QUERY_NAME_SORTBY } from "@/Constants";
import {
  isValidOrderSelection,
  sortingOrderSelections,
} from "@/util/sortingSelection.ts";

const { t } = useI18n();

const orderQuery = useRouteQuery(QUERY_NAME_ORDER);
const orderByQuery = useRouteQuery(QUERY_NAME_SORTBY);

/**
 * Internal state of the current selected sorting criteria.
 */
const sortingCriteria = ref<CriteriaValue>({ criteria: "title", order: "asc" });

/**
 * Initializes sorting criteria from the query parameters when the component is mounted.
 */
onMounted(() => {
  if (
    orderQuery.value &&
    orderByQuery.value &&
    isValidOrderSelection({
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

/**
 * Watches for changes in sorting criteria and updates the corresponding query parameters.
 */
watch(sortingCriteria, (newSortingCriteria) => {
  orderQuery.value = newSortingCriteria.order;
  orderByQuery.value = newSortingCriteria.criteria;
});
</script>
