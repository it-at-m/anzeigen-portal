<template>
  <ad-chip
    :icon="computedIcon"
    :text="t(`ad.type.${adType!.toLocaleLowerCase()}`)"
  />
</template>

<script setup lang="ts">
import type { AdTOAdTypeEnum } from "@/api/swbrett";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import AdChip from "@/components/common/AdChip.vue";

const { t } = useI18n();
const { adType } = defineProps<{
  adType?: AdTOAdTypeEnum;
}>();

/**
 * Computes the icon based on the offer status.
 */
const computedIcon = computed(() => {
  switch (adType) {
    case "OFFER":
      return "mdi-hand-extended-outline";
    case "SEEK":
      return "mdi-binoculars";
    case "RENTAL":
      return "mdi-file-sign";
    default:
      return "mdi-help";
  }
});
//  adType != null ?  :
</script>

<style scoped></style>
