<template>
  <ad-display-card>
    <template #title>{{ t("filterAdType.title") }}</template>
    <template #text>
      <v-checkbox
        v-model="isSeek"
        :label="t('ad.type.seek')"
        hide-details
        density="compact"
        color="accent"
      />
      <v-checkbox
        v-model="isOffer"
        :label="t('ad.type.offer')"
        hide-details
        density="compact"
        color="accent"
      />
      <v-checkbox
        v-model="isRental"
        :label="t('ad.type.rental')"
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
const isRental = ref<boolean>(true);

const typeQuery = useRouteQuery(QUERY_NAME_TYPE);

/**
 * Initializes checkbox selections based on the "type" URL parameter.
 * Deselects any type which is not in URL parameter if the URL parameter is set.
 */
onMounted(() => {
  if (!typeQuery.value) {
    return;
  }

  const typeQueryValue = Array.isArray(typeQuery.value)
    ? typeQuery.value
    : [typeQuery.value];

  isOffer.value = typeQueryValue.includes("OFFER");
  isSeek.value = typeQueryValue.includes("SEEK");
  isRental.value = typeQueryValue.includes("RENTAL");
});

/**
 * Watches changes in the checkbox selections and adjusts selections and URL parameter accordingly.
 * Ensures at least one option (Offer, Seek or Rental) remains selected at all times.
 * If all options are selected, the URL parameter is cleared.
 */
watch(
  [isOffer, isSeek, isRental],
  (
    [newIsOffer, newIsSeek, newIsRental],
    [oldIsOffer, oldIsSeek, oldIsRental]
  ) => {
    if (!newIsOffer && !newIsSeek && !newIsRental) {
      isOffer.value = oldIsOffer;
      isSeek.value = oldIsSeek;
      isRental.value = oldIsRental;
    }

    // Update URL parameter
    if (isOffer.value && isSeek.value && isRental.value) {
      typeQuery.value = null;
    } else {
      typeQuery.value = [
        ...(isOffer.value ? ["OFFER"] : []),
        ...(isSeek.value ? ["SEARCH"] : []),
        ...(isRental.value ? ["RENTAL"] : []),
      ];
    }
  }
);
</script>
