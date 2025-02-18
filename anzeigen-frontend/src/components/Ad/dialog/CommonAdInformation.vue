<template>
  <v-radio-group
    v-model="adTO.adType"
    :disabled="disabled"
    color="accent"
    inline
  >
    <v-radio
      :label="t('ad.type.offer')"
      value="OFFER"
    />
    <v-radio
      :label="t('ad.type.search')"
      value="SEEK"
    />
  </v-radio-group>
  <v-text-field
    v-model="adTO.title"
    variant="outlined"
    density="compact"
    color="accent"
    :disabled="disabled"
    class="mb-4"
    hide-details="auto"
    :label="t('commonAdInformation.titleLabel')"
    :rules="[
      (value) => !!value || 'Bitte geben Sie einen Titel ein.',
      (value) =>
        value.length < AD_MAX_TITLE_LENGTH || 'Bitte kürzen Sie den Titel',
    ]"
  />
  <ad-category-selector
    v-model="adTO.adCategory"
    variant="outlined"
    :disabled="disabled"
    hide-details="auto"
  />
  <v-textarea
    v-model="adTO.description"
    variant="outlined"
    density="compact"
    color="accent"
    :label="t('commonAdInformation.description')"
    class="my-4"
    hide-details="auto"
    max-rows="3"
    :disabled="disabled"
    :rules="[(value) => !!value || t('commonAdInformation.ruleMsg')]"
  />
  <ad-price-selection
    v-model="adTO.price!"
    :disabled="disabled"
  />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { ref, watch } from "vue";
import { useI18n } from "vue-i18n";

import AdCategorySelector from "@/components/Ad/dialog/common/AdCategorySelector.vue";
import AdPriceSelection from "@/components/Ad/dialog/common/AdPriceSelection.vue";
import { AD_MAX_TITLE_LENGTH, EMPTY_ADTO_OBJECT } from "@/Constants";

const { t } = useI18n();
const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

const price = defineModel<number>("price", { default: 1 });

defineProps<{
  disabled?: boolean;
}>();

const priceOption = ref<number>(Math.sign(adTO.value.price ?? 1));

/**
 * Watches for changes in the price option and updates the price accordingly.
 * Ensures that the price is adjusted based on the selected price option (e.g., fixed, negotiable, or free).
 */
watch(priceOption, (newPriceOption) => {
  price.value = (newPriceOption ?? 1) * Math.abs(price.value ?? 1);
});
</script>

<style scoped></style>
