<template>
  <account-card />
  <v-btn
    class="w-100 mb-4"
    prepend-icon="mdi-plus"
    size="large"
    color="accent"
    flat
  >
    Anzeige erstellen
  </v-btn>
  <ad-display-sheet class="mb-4">
    <filter-ad-category />
    <filter-ad-type />
  </ad-display-sheet>

  <sort-ad-selection />
</template>

<script setup lang="ts">
import AccountCard from "@/components/common/AccountCard.vue";
import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import FilterAdCategory from "@/components/Filter/FilterAdCategory.vue";
import FilterAdType from "@/components/Filter/FilterAdType.vue";
import SortAdSelection from "@/components/Filter/SortAdSelection.vue";
</script>

<style scoped></style>
