<template>
  <v-text-field
    class="w-md-66 w-sm-75"
    :model-value="displayValue"
    color="accent"
    label="Ablaufdatum"
    type="date"
    prepend-icon="mdi-calendar-range"
    :disabled="disabled"
    :rules="[isDate, isMinDate]"
    @update:model-value="valueChanged"
  />
</template>

<script setup lang="ts">
import { computed } from "vue";

const { modelValue } = defineProps<{
  modelValue: Date | undefined;
  disabled?: boolean;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: Date];
}>();

/**
 * Converts the modelValue string to format of "YYYY-MM-DD" which is necessary for html input of type date
 */
const displayValue = computed(() => {
  // locales fr-CA produces format of YYYY-MM-DD which is needed for input(type=date)
  return modelValue
    ? new Date(modelValue).toLocaleDateString("fr-CA", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      })
    : "";
});

/**
 * Emits selected date as ISO string if it is valid
 * @param value current selected date
 */
const valueChanged = (value: string) => {
  const convertedDate = new Date(value);
  if (!isNaN(convertedDate.getTime())) {
    emit("update:modelValue", new Date(value));
  }
};

/**
 * Date must be valid
 * @param value inputted date
 */
const isDate = (value: string) => !!value || "Das Datum is nicht valide";

/**
 * Date must be in at least today or in the future
 * @param value selected date
 */
const isMinDate = (value: string) => {
  const selectedDate = new Date(value);
  return (
    (selectedDate && selectedDate >= new Date()) ||
    "Das Datum darf nicht in der Vergangenheit liegen!"
  );
};
</script>

<style scoped></style>
