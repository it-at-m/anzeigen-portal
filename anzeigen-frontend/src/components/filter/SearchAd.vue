<template>
  <v-text-field
    id="searchField"
    v-model="searchValue"
    flat
    variant="solo-inverted"
    hide-details
    :label="t('searchAd.label')"
    clearable
    prepend-inner-icon="mdi-magnify"
    theme="dark"
    @keyup="debouncedSearch"
    @keyup.enter="search"
    @click:clear="search"
  />
</template>

<script setup lang="ts">
import { useDebounceFn } from "@vueuse/core";
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

/**
 * Internal representation of the current search value.
 */
const searchValue = ref<string>();

const searchQuery = useRouteQuery("searchTerm");

/**
 * Initializes search value from the query parameter when the component is mounted.
 */
onMounted(() => {
  if (searchQuery.value && searchQuery.value !== "") {
    searchValue.value = searchQuery.value.toString();
  }
});

/**
 * Debounced function to update the query only every 1s
 */
const debouncedSearch = useDebounceFn(
  () => {
    searchQuery.value = searchValue.value ?? null;
  },
  1000,
  { maxWait: 2000 }
);

/**
 * Updates the search query parameter based on the current search value.
 */
const search = () => {
  searchQuery.value = searchValue.value ?? null;
};
</script>

<style scoped></style>
