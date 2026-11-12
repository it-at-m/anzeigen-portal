<template>
  <ad-chip
    :icon="computedIcon"
    :text="t(`ad.type.${adType!.toLocaleLowerCase()}`)"
  />
</template>

<script setup lang="ts">
import type { AdTOAdTypeEnum } from "@/apis/generated/anzeigen-backend";

import {
  mdiBinoculars,
  mdiFileSign,
  mdiHandExtendedOutline,
  mdiHelp,
} from "@mdi/js";
import { computed } from "vue";
import { useI18n } from "vue-i18n";

import AdChip from "@/components/common/AdChip.vue";

const { t } = useI18n();
const { adType = undefined } = defineProps<{
  adType?: AdTOAdTypeEnum;
}>();

/**
 * Computes the icon based on the offer status.
 */
const computedIcon = computed(() => {
  switch (adType) {
    case "OFFER":
      return mdiHandExtendedOutline;
    case "SEEK":
      return mdiBinoculars;
    case "RENTAL":
      return mdiFileSign;
    default:
      return mdiHelp;
  }
});
//  adType != null ?  :
</script>

<style scoped></style>
