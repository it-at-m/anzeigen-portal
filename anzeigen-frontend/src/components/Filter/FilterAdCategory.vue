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
import { useGetCategories } from "@/composables/api/useGetCategories";
import { useSnackbar } from "@/composables/useSnackbar";
import { API_ERROR_MSG, QUERY_NAME_CATEGORYID } from "@/Constants";
import { useCategoriesStore } from "@/stores/adcategory";

const NO_CATEGORY = { id: -1, name: "Alle", standard: true };

const { call: getCategories, data, loading, error } = useGetCategories();

const snackbar = useSnackbar();

const categoryQuery = useRouteQuery(QUERY_NAME_CATEGORYID);

const selectedCategory = ref<AdCategory>(NO_CATEGORY);

const categoriesStore = useCategoriesStore();

onMounted(async () => {
  if (!categoriesStore.isEmpty) {
    return;
  }

  await getCategories();

  // error catching
  if (error.value) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
  }

  categoriesStore.setCategories(data.value as AdCategory[]);

  // initial selection
  if (categoryQuery.value && categoriesStore.categories.length !== 0) {
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
    categoryQuery.value = null;
  } else {
    categoryQuery.value = newSelectedCategory.id.toString();
  }
});
</script>

<style scoped></style>
