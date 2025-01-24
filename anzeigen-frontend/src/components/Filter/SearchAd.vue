<template>
  <v-text-field
    id="searchField"
    v-model="searchValue"
    flat
    variant="solo-inverted"
    hide-details
    label="Suche"
    clearable
    prepend-inner-icon="mdi-magnify"
    theme="dark"
    @keyup.enter="search"
    @click:clear="search"
  />
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref } from "vue";

/**
 * Internal representation of the current search value.
 */
const searchValue = ref<string>();

// TODO: do not constantly update the query upon keypress - maybe use a debouncer and search every 1-2s :D

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
 * Updates the search query parameter based on the current search value.
 */
const search = () => {
  searchQuery.value = searchValue.value ?? null;
};
</script>

<style scoped></style>
