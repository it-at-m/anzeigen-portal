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
            v-for="category in data"
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
import { useGetCategories } from "@/composables/api/useGetCategories";
import { useSnackbar } from "@/composables/useSnackbar";
import { API_ERROR_MSG } from "@/Constants";
import { useCategoriesStore } from "@/stores/adcategory";

const NO_CATEGORY = { id: -1, name: "Alle", standard: true };

const { call: getCategories, data, loading, error } = useGetCategories();

const snackbar = useSnackbar();

const categoryQuery = useRouteQuery("categoryId");

const selectedCategory = ref<AdCategory>(NO_CATEGORY);

const categoriesStore = useCategoriesStore();

onMounted(async () => {
  // initial api-call

  await getCategories().then(() =>
    categoriesStore.setCategories(data.value as AdCategory[])
  );

  // error catching
  if (error.value) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
  }

  // initial selection
  if (categoryQuery.value && categoriesStore.categories !== undefined) {
    // search for matching category and set it
    selectedCategory.value =
      categoriesStore.categories.find(
        (category) =>
          category.id && category.id.toString() === categoryQuery.value
      ) || selectedCategory.value;
  }
});

watch(selectedCategory, (newSelectedCategory) => {
  if (
    newSelectedCategory === undefined ||
    !newSelectedCategory.id ||
    newSelectedCategory?.id === -1
  ) {
    categoryQuery.value = [];
  } else {
    categoryQuery.value = newSelectedCategory.id.toString();
  }
});

/**
 * Loads categories from the backend and save them to the store
 */
const loadCategories = async () => {
  await getCategories();

  console.log("Categories data", data.value);

  if (error.value) {
    snackbarStore.showMessage({
      message: "Kategorien konnten nicht geladen werden.",
      level: Levels.ERROR,
    });
  } else {
    console.log("Setting categories into store");
    categoriesStore.setCategories(data.value as AdCategory[]);
  }
};
</script>

<style scoped></style>
