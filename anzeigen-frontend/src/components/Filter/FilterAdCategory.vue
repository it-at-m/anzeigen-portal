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
import { useSnackbarStore } from "@/stores/snackbar";

const NO_CATEGORY = { id: -1, name: "Alle", standard: true };

const { call: getCategories, data, loading, error } = useGetCategories();
const snackbarStore = useSnackbarStore();

const categoryQuery = useRouteQuery("categoryId");

const selectedCategory = ref<AdCategory>(NO_CATEGORY);

onMounted(async () => {
  // initial api-call
  await getCategories();

  // error catching
  if (error.value) {
    // TODO: Does not work yet
    snackbarStore.showMessage({
      message: "Hilfe hier ist ein Fehler aufgetreten",
      level: Levels.ERROR,
    });
  }

  // initial selection
  if (categoryQuery.value && data.value !== undefined) {
    // search for matching category and set it
    selectedCategory.value =
      data.value.find(
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
</script>

<style scoped></style>
