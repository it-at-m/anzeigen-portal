<template>
  <ad-display-card>
    <template #title>{{ t("filterAdActive.title") }}</template>
    <template #text>
      <v-radio-group
        v-model="activeAds"
        color="accent"
      >
        <v-radio
          :label="t('filterAdActive.labelActive')"
          :value="true"
        />
        <v-radio
          :label="t('filterAdActive.labelInactive')"
          :value="false"
        />
      </v-radio-group>
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
