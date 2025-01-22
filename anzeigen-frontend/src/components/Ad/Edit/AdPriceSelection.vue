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

const price = computed(() => Math.abs(modelValue));

const priceOption = computed(() => Math.sign(modelValue));

const updatedPrice = (updatedPrice: number) => {
  emit("update:modelValue", priceOption.value * updatedPrice);
};

const updatePriceOption = (updatedPriceOption: number | null) => {
  console.log(updatedPriceOption);

  emit(
    "update:modelValue",
    (updatedPriceOption ?? 1) * Math.max(1, price.value)
  );
};
</script>

<style scoped></style>
