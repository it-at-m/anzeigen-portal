<template>
  <account-card />
  <ad-edit-button
    class="w-100 mb-4"
    size="large"
    flat
    @click="triggerDialog"
  />
  <ad-display-sheet class="mb-4">
    <filter-ad-category />
    <filter-ad-type />
  </ad-display-sheet>

  <sort-ad-selection />
</template>

<script setup lang="ts">
import { useEventBus } from "@vueuse/core";

import AdEditButton from "@/components/Ad/AdEditButton.vue";
import AccountCard from "@/components/common/AccountCard.vue";
import AdDisplaySheet from "@/components/common/AdDisplaySheet.vue";
import FilterAdCategory from "@/components/Filter/FilterAdCategory.vue";
import FilterAdType from "@/components/Filter/FilterAdType.vue";
import SortAdSelection from "@/components/Filter/SortAdSelection.vue";
import { EV_EDIT_AD_DIALOG } from "@/Constants";

const dialogBus = useEventBus<boolean>(EV_EDIT_AD_DIALOG);

const triggerDialog = () => {
  dialogBus.emit(true);
};
</script>

<style scoped></style>
