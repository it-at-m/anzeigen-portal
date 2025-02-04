<template>
  <ad-display-card>
    <template #title>Nach Kategorie stöbern</template>
    <template #text>
      <v-skeleton-loader
        type="heading@8"
        :loading="categoriesStore.isEmpty"
      >
        <v-tabs
          v-model="selectedCategoryId"
          class="w-100"
          direction="vertical"
        >
          <v-tab
            :key="NO_CATEGORY.id"
            :value="NO_CATEGORY.id"
          >
            <template #prepend>
              <v-icon
                color="accent"
                icon="mdi-check-all"
              />
            </template>
            {{ NO_CATEGORY.name }}
          </v-tab>
          <v-tab
            v-for="category in categoriesStore.categories"
            :key="category.id"
            :value="category.id"
          >
            <template #prepend>
              <v-icon
                color="accent"
                icon="mdi-card-multiple"
              />
            </template>
            {{ category.name }}
          </v-tab>
        </v-tabs>
      </v-skeleton-loader>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { NO_CATEGORY, QUERY_NAME_CATEGORYID } from "@/Constants.ts";
import { useCategoriesStore } from "@/stores/adcategory";

const categoriesStore = useCategoriesStore();

const categoryQuery = useRouteQuery(QUERY_NAME_CATEGORYID);

/**
 * Internal state of current selected category.
 */
const selectedCategoryId = ref<number>(-1);

/**
 * Initializes and manages category selection and data fetching for categories.
 */
onMounted(() => {
  selectedCategoryId.value = categoryQuery.value
    ? parseInt(categoryQuery.value.toString())
    : -1;
});

/**
 * Watches for changes in the selected category and updates the query parameter accordingly.
 */
watch(selectedCategoryId, (newSelectedCategoryId) => {
  categoryQuery.value =
    newSelectedCategoryId === -1 ? null : newSelectedCategoryId.toString();
});
</script>

<style scoped></style>
