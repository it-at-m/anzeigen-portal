<template>
  <v-text-field
    v-model="title"
    label="Titel"
    class="w-md-66 w-sm-75"
    :disabled="disabled"
    :rules="[
      (value) => !!value || 'Bitte geben Sie einen Titel ein.',
      (value) =>
        value.length < AD_MAX_TITLE_LENGTH || 'Bitte kürzen Sie den Titel',
    ]"
  />
  <v-select
    v-model="category"
    class="w-md-66 w-sm-75"
    placeholder="Kategorie"
    :loading="categoryLoading"
    :disabled="disabled"
    :items="data as AdCategory[]"
    item-title="name"
    :rules="[(value) => !!value || 'Bitte wählen Sie eine Kategorie aus.']"
  />
  <v-radio-group
    v-model="adType"
    :disabled="disabled"
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
    v-model="description"
    label="Beschreibung"
    max-rows="3"
    :disabled="disabled"
    :rules="[
      (value) =>
        !!value ||
        'Bitte geben Sie eine Beschreibung mit maximal 1000 Zeichen ein.',
    ]"
  />
  <v-number-input
    label="Preis"
    class="w-md-66 w-sm-75"
    :min="0"
    :disabled="priceOption === 0 || disabled"
    :model-value="displayedPrice"
    @update:model-value="updatedPrice"
  >
    <template #append-inner>
      <v-icon
        class="mr-2"
        icon="mdi-currency-eur"
      />
    </template>
  </v-number-input>
  <v-radio-group
    v-model="priceOption"
    :disabled="disabled"
    inline
  >
    <v-radio
      label="Festpreis"
      :value="1"
    />
    <v-radio
      label="VB"
      :value="-1"
    />
    <v-radio
      label="Zu verschenken"
      :value="0"
    />
  </v-radio-group>
</template>

<script setup lang="ts">
import type { AdCategory, AdTOAdTypeEnum } from "@/api/swbrett";

import { computed, onMounted, ref, watch } from "vue";
import { VNumberInput } from "vuetify/labs/components";

import { useGetCategories } from "@/composables/api/useGetCategories";
import { AD_MAX_TITLE_LENGTH } from "@/Constants";

const {
  call: getCategories,
  data,
  loading: categoryLoading,
} = useGetCategories();

const title = defineModel<string>("title", { default: "" });

const category = defineModel<AdCategory>("category");

const adType = defineModel<AdTOAdTypeEnum>("adType", {
  default: "OFFER",
});

const description = defineModel<string>("description", { default: "" });

const price = defineModel<number>("price", { default: 1 });

defineProps<{
  disabled?: boolean;
}>();

const priceOption = ref<number>(Math.sign(price.value ?? 1));

onMounted(() => {
  getCategories();
});

watch(priceOption, (newPriceOption) => {
  price.value = (newPriceOption ?? 1) * Math.abs(price.value ?? 1);
});

const displayedPrice = computed(() => Math.abs(price.value ?? 0));

const updatedPrice = (newPrice: number) =>
  (price.value = newPrice * priceOption.value);
</script>

<style scoped></style>
