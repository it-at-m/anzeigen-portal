<template>
  <ad-display-card>
    <template #title>Nach Kategorie stöbern</template>
    <template #text>
      <v-skeleton-loader
        type="heading@8"
        :loading="loading"
      >
        <v-tabs
          v-model="selectedCategory"
          class="w-100"
          direction="vertical"
        >
          <v-tab
            :key="-1"
            :value="NO_CATEGORY"
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
            :value="category"
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
import type { AdCategory } from "@/api/swbrett";

import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";

import { Levels } from "@/api/error";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useCategoriesApi } from "@/composables/api/useCategoriesApi.ts";
import { useSnackbar } from "@/composables/useSnackbar";
import { API_ERROR_MSG, QUERY_NAME_CATEGORYID } from "@/Constants";
import { useCategoriesStore } from "@/stores/adcategory";

/**
 * Standard for no category selection.
 */
const NO_CATEGORY = { id: -1, name: "Alle", standard: true };

const { call: getCategories, data, loading, error } = useCategoriesApi();

const snackbar = useSnackbar();

const categoriesStore = useCategoriesStore();

const categoryQuery = useRouteQuery(QUERY_NAME_CATEGORYID);

/**
 * Internal state of current selected category.
 */
const selectedCategory = ref<AdCategory>(NO_CATEGORY);

/**
 * Initializes and manages category selection and data fetching for categories.
 */
onMounted(async () => {
  // If categories are already loaded, do nothing.
  if (!categoriesStore.isEmpty) {
    return;
  }

  // Fetch categories from the API.
  await getCategories();

  // Error handling: show a snackbar if the API call fails.
  if (error.value) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
  }

  // Store the fetched categories.
  categoriesStore.setCategories(data.value as AdCategory[]);

  // Set initial category selection if a query is provided.
  if (categoryQuery.value && categoriesStore.categories.length !== 0) {
    // search for matching category and set it
    selectedCategory.value =
      categoriesStore.categories.find(
        (category) =>
          category.id && category.id.toString() === categoryQuery.value
      ) || selectedCategory.value;
  }
});

/**
 * Watches for changes in the selected category and updates the query parameter accordingly.
 */
watch(selectedCategory, (newSelectedCategory) => {
  if (
    newSelectedCategory === undefined ||
    !newSelectedCategory.id ||
    newSelectedCategory?.id === -1
  ) {
    categoryQuery.value = null;
  } else {
    categoryQuery.value = newSelectedCategory.id.toString();
  }
});
</script>

<style scoped></style>
