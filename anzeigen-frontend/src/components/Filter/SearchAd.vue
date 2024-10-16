<template>
  <v-text-field
    id="searchField"
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
import Card from "@/components/common/Card.vue";

const search = () => {};
</script>

<style scoped></style>
