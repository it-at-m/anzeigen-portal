<template>
  <v-text-field
    v-model="adTO.title"
    color="accent"
    :disabled="disabled"
    label="Titel"
    class="w-md-66 w-sm-75"
    :rules="[
      (value) => !!value || 'Bitte geben Sie einen Titel ein.',
      (value) =>
        value.length < AD_MAX_TITLE_LENGTH || 'Bitte kürzen Sie den Titel',
    ]"
  />
  <ad-category-selector v-model="adTO.adCategory" />
  <v-radio-group
    v-model="adTO.adType"
    :disabled="disabled"
    color="accent"
    inline
  >
    <v-radio
      label="Biete"
      value="OFFER"
    />
    <v-radio
      label="Suche"
      value="SEEK"
    />
  </v-radio-group>
  <v-textarea
    v-model="adTO.description"
    color="accent"
    label="Beschreibung"
    max-rows="3"
    :disabled="disabled"
    :rules="[
      (value) =>
        !!value ||
        'Bitte geben Sie eine Beschreibung mit maximal 1000 Zeichen ein.',
    ]"
  />
  <ad-price-selection
    v-model="adTO.price!"
    :disabled="disabled"
  />
</template>

<script setup lang="ts">
import type { AdCategory, AdTO, AdTOAdTypeEnum } from "@/api/swbrett";

import { computed, ref, watch } from "vue";

import AdCategorySelector from "@/components/Ad/Edit/AdCategorySelector.vue";
import AdPriceSelection from "@/components/Ad/Edit/AdPriceSelection.vue";
import { AD_MAX_TITLE_LENGTH, EMPTY_ADTO_OBJECT } from "@/Constants";
import { useCategoriesStore } from "@/stores/adcategory";

const categoryStore = useCategoriesStore();

const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

const adTitle = defineModel<string>("title", { default: "" });

const category = ref<AdCategory>();

const adType = defineModel<AdTOAdTypeEnum>("adType", {
  default: "OFFER",
});

const description = defineModel<string>("description", { default: "" });

const price = defineModel<number>("price", { default: 1 });

defineProps<{
  disabled?: boolean;
}>();

const priceOption = ref<number>(Math.sign(adTO.value.price ?? 1));

watch(priceOption, (newPriceOption) => {
  price.value = (newPriceOption ?? 1) * Math.abs(price.value ?? 1);
});

const displayedPrice = computed(() => Math.abs(price.value ?? 0));

const updatedPrice = (newPrice: number) =>
  (price.value = newPrice * priceOption.value);
</script>

<style scoped></style>
