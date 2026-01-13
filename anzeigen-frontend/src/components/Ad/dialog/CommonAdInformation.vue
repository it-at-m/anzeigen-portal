<template>
  <v-row>
    <v-col
      cols="12"
      md="7"
    >
      <v-radio-group
        v-model="adTO.adType"
        :disabled="disabled"
        color="accent"
        hide-details="auto"
        inline
      >
        <v-radio
          :label="t('ad.type.offer')"
          value="OFFER"
        />
        <v-radio
          :label="t('ad.type.seek')"
          value="SEEK"
        />
        <v-radio
          :label="t('ad.type.rental')"
          value="RENTAL"
        />
      </v-radio-group>
    </v-col>
    <v-col
      v-if="adTO.adType === 'RENTAL'"
      cols="12"
      md="5"
    >
      <ad-date-selector
        v-model="adTO.rentalDate"
        :label="t('commonAdInformation.rentalDate')"
        max-date-setting="MAX_RENTAL_DATE_RANGE"
        :disabled="disabled"
      />
    </v-col>
  </v-row>
  <v-text-field
    v-model="adTO.title"
    variant="outlined"
    density="compact"
    color="accent"
    :disabled="disabled"
    class="my-4"
    hide-details="auto"
    :label="t('commonAdInformation.titleLabel')"
    :rules="[
      (value) => !!value || t('commonAdInformation.ruleMsg.title'),
      (value) =>
        value.length < AD_MAX_TITLE_LENGTH ||
        t('commonAdInformation.ruleMsg.titleToLong'),
    ]"
  />
  <v-row>
    <v-col
      cols="12"
      md="6"
    >
      <ad-category-selector
        v-model="adTO.adCategory"
        variant="outlined"
        :disabled="disabled"
        hide-details="auto"
      />
    </v-col>
    <v-col
      cols="12"
      md="6"
    >
      <ad-condition-selector
        v-model="adTO.condition"
        :disabled="disabled"
        variant="outlined"
        hide-details="auto"
      />
    </v-col>
  </v-row>
  <v-textarea
    v-model="adTO.description"
    variant="outlined"
    density="compact"
    color="accent"
    :label="t('commonAdInformation.descriptionLabel')"
    class="my-4"
    hide-details="auto"
    max-rows="3"
    :disabled="disabled"
    :rules="[
      (value) => !!value || t('commonAdInformation.ruleMsg.description'),
    ]"
  />
  <ad-price-selection
    v-if="CONFIG.SHOW_PRICE"
    v-model="adTO.price!"
    :disabled="disabled"
  />
  <address-selection v-model="adTO.address" />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { ref, watch } from "vue";
import { useI18n } from "vue-i18n";

import AdCategorySelector from "@/components/Ad/dialog/common/AdCategorySelector.vue";
import AdConditionSelector from "@/components/Ad/dialog/common/AdConditionSelector.vue";
import AddressSelection from "@/components/Ad/dialog/common/AddressSelection.vue";
import AdPriceSelection from "@/components/Ad/dialog/common/AdPriceSelection.vue";
import AdDateSelector from "@/components/Ad/dialog/seller/AdDateSelector.vue";
import { AD_MAX_TITLE_LENGTH, CONFIG, EMPTY_ADTO_OBJECT } from "@/Constants";

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
