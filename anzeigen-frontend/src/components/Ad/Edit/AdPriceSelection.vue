<template>
  <v-row>
    <v-col
      cols="12"
      sm="5"
    >
      <v-number-input
        variant="outlined"
        density="compact"
        label="Preis"
        hide-details
        :min="0"
        color="accent"
        :disabled="priceOption === 0 || disabled"
        :model-value="price"
        @update:model-value="updatedPrice"
      >
        <template #append-inner>
          <v-icon
            class="mr-2"
            icon="mdi-currency-eur"
          />
        </template>
      </v-number-input>
    </v-col>
    <v-col
      cols="12"
      sm="7"
    >
      <v-select
        v-model="priceOption"
        density="compact"
        :disabled="disabled"
        :items="selectionArray"
        color="accent"
        variant="outlined"
        @update:model-value="updatePriceOption"
      />
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { VNumberInput } from "vuetify/labs/components";

/**
 * Array of available price options, representing different pricing methods.
 */
const selectionArray = [
  {
    title: "Festpreis",
    value: 1,
  },
  {
    title: "Verhandlungsbasis",
    value: -1,
  },
  {
    title: "Zu verschenken",
    value: 0,
  },
];

const { modelValue = 1 } = defineProps<{
  disabled?: boolean;
  modelValue: number;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: number];
}>();

/**
 * Computes the absolute value of the model's price.
 */
const price = computed(() => Math.abs(modelValue));

/**
 * Computes the sign of the price, representing whether it's a fixed price, negotiable, or free.
 */
const priceOption = computed(() => Math.sign(modelValue));

/**
 * Updates the model value with the new price, adjusting for the price option (e.g., negotiable or free).
 * @param updatedPrice - The new price value.
 */
const updatedPrice = (updatedPrice: number) => {
  emit("update:modelValue", priceOption.value * updatedPrice);
};

/**
 * Updates the model value based on a new price option, adjusting for the price's magnitude.
 * @param updatedPriceOption - The new price option to apply (e.g., fixed, negotiable, free).
 */
const updatePriceOption = (updatedPriceOption: number | null) =>
  emit(
    "update:modelValue",
    (updatedPriceOption ?? 1) * Math.max(1, price.value)
  );
</script>

<style scoped></style>
