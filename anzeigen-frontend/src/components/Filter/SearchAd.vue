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
  />
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref } from "vue";

const searchValue = ref<string>();

// TODO: do not constantly update the query upon keypress - rather update it on keyup.enter!

const searchQuery = useRouteQuery("searchTerm");

onMounted(() => {
  if (searchQuery.value && searchQuery.value !== "") {
    searchValue.value = searchQuery.value.toString();
  }
});

const search = () => {
  searchQuery.value = searchValue.value ?? "";
};
</script>

<style scoped></style>
