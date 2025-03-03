<template>
  <ad-display-card>
    <template #title>{{ t("filterAdType.title") }}</template>
    <template #text>
      <v-checkbox
        v-model="isSeek"
        label="Suche"
        hide-details
        density="compact"
        color="accent"
      />
      <v-checkbox
        v-model="isOffer"
        label="Biete"
        hide-details
        density="compact"
        color="accent"
      />
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { useRouteQuery } from "@vueuse/router";
import { onMounted, ref, watch } from "vue";
import { useI18n } from "vue-i18n";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { QUERY_NAME_TYPE } from "@/Constants";

const { t } = useI18n();
/**
 * Internal state of current selected ad-type. Both can be true!
 */
const isOffer = ref<boolean>(true);
const isSeek = ref<boolean>(true);

const typeQuery = useRouteQuery(QUERY_NAME_TYPE);

/**
 * Initializes checkbox selections based on the "type" URL parameter.
 * Sets either "Offer" or "Seek" as deselected if the URL specifies a type.
 */
onMounted(() => {
  if (typeQuery.value === "OFFER") {
    isSeek.value = false;
  } else if (typeQuery.value === "SEEK") {
    isOffer.value = false;
  }
});

/**
 * Watches changes in the checkbox selections and adjusts selections and URL parameter accordingly.
 * Ensures at least one option (Offer or Seek) remains selected at all times.
 * If both options are selected, the URL parameter is cleared.
 */
watch([isOffer, isSeek], ([newIsOffer, newIsSeek], [oldIsOffer, oldIsSeek]) => {
  if (!newIsOffer && !oldIsSeek) {
    isSeek.value = true;
  } else if (!newIsSeek && !oldIsOffer) {
    isOffer.value = true;
  }

  // Update URL parameter
  if (isOffer.value && isSeek.value) {
    typeQuery.value = null;
  } else {
    typeQuery.value = isOffer.value ? "OFFER" : "SEEK";
  }
});
</script>
