<template>
  <v-number-input
    label="Preis"
    class="w-md-66 w-sm-75"
    :min="0"
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
  <v-radio-group
    v-model="priceOption"
    :disabled="disabled"
    inline
    @update:model-value="updatePriceOption"
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
import { computed } from "vue";
import { VNumberInput } from "vuetify/labs/components";

const { modelValue = 1 } = defineProps<{
  disabled?: boolean;
  modelValue: number;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: number];
}>();

const price = computed(() => Math.abs(modelValue));

const priceOption = computed(() => Math.sign(modelValue));

const updatedPrice = (updatedPrice: number) => {
  emit("update:modelValue", priceOption.value * updatedPrice);
};

const updatePriceOption = (updatedPriceOption: number | null) => {
  emit(
    "update:modelValue",
    (updatedPriceOption ?? 1) * Math.max(1, price.value)
  );
};
</script>

<style scoped></style>
