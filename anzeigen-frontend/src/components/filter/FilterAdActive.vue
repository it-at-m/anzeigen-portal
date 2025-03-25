<template>
  <ad-display-card>
    <template #title>Inaktive Anzeigen</template>
    <template #text>
      <v-checkbox
        v-model="activeAds"
        label="Aktive Anzeigen"
        hide-details
        density="compact"
        color="accent"
      />
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { ref, watch } from "vue";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { QUERY_IS_ACTIVE } from "@/Constants.ts";

const activeAds = ref<boolean>(true);

const isActiveQuery = useRouteQuery(QUERY_IS_ACTIVE);

watch(activeAds, (newActiveAds) => {
  isActiveQuery.value = newActiveAds.toString();
});
</script>

<style scoped></style>
