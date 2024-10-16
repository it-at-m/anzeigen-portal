<template>
  <sheet>
    <filter-ad-type />
    <filter-ad-category />
    <sort-ad-selection />
  </sheet>
</template>

<script setup lang="ts">
import Sheet from "@/components/common/Sheet.vue";
import FilterAdCategory from "@/components/Filter/FilterAdCategory.vue";
import FilterAdType from "@/components/Filter/FilterAdType.vue";
import SortAdSelection from "@/components/Filter/SortAdSelection.vue";
</script>

<style scoped></style>
