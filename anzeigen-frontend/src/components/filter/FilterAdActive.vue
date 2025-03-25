<template>
  <ad-display-card>
    <template #title>{{ t("filterAdActive.title") }}</template>
    <template #text>
      <v-checkbox
        v-model="activeAds"
        :label="t('filterAdActive.label')"
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
import { useI18n } from "vue-i18n";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { QUERY_IS_ACTIVE } from "@/Constants.ts";

const { t } = useI18n();

const activeAds = ref<boolean>(true);

const isActiveQuery = useRouteQuery(QUERY_IS_ACTIVE);

watch(activeAds, (newActiveAds) => {
  isActiveQuery.value = newActiveAds.toString();
});
</script>

<style scoped></style>
