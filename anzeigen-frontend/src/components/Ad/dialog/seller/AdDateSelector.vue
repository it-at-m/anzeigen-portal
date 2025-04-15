<template>
  <v-text-field
    variant="outlined"
    :model-value="displayValue"
    density="compact"
    color="accent"
    hide-details="auto"
    type="date"
    prepend-icon="mdi-calendar-range"
    :disabled="disabled"
    :rules="[isDate, isMinDate, isMaxDate]"
    @update:model-value="valueChanged"
  />
</template>

<script setup lang="ts">
import type { SettingTOSettingNameEnum } from "@/api/swbrett";

import { computed } from "vue";
import { useI18n } from "vue-i18n";

import { useSettingStore } from "@/stores/settings.ts";

const { t } = useI18n();

const DAYS_IN_WEEK = 7;

const settingStore = useSettingStore();
/**
 * The amount of weeks in advance ... why weeks? idk
 */
const maxAdvanceDate = computed(() => {
  const newDate = new Date();
  newDate.setDate(
    newDate.getDate() +
      DAYS_IN_WEEK * (settingStore.getSetting(maxDateSetting)?.numberValue || 4)
  );
  return newDate;
});

const { modelValue, maxDateSetting } = defineProps<{
  modelValue: Date | undefined;
  maxDateSetting: SettingTOSettingNameEnum;
  disabled?: boolean;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: Date];
}>();

/**
 * computed the displayed date, ever the already selected or a predefined maximum value
 */
const computedDate = computed(() =>
  !modelValue ? maxAdvanceDate.value : modelValue
);

/**
 * Converts the modelValue string to format of "YYYY-MM-DD" which is necessary for html input of type date
 */
const displayValue = computed(() => {
  // locales fr-CA produces format of YYYY-MM-DD which is needed for input(type=date)
  return new Date(computedDate.value).toLocaleDateString("fr-CA", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
});

/**
 * Emits selected date as ISO string if it is valid
 * @param value current selected date
 */
const valueChanged = (value: string) => {
  const convertedDate = new Date(value);
  if (!isNaN(convertedDate.getTime())) {
    emit("update:modelValue", convertedDate);
  }
};

/**
 * Date must be valid
 * @param value inputted date
 */
const isDate = (value: string) =>
  !value ||
  !isNaN(new Date(value).getDate()) ||
  t("adDateSelector.ruleMsg.invalid");

/**
 * Date must be in at least today or in the future
 * @param value selected date
 */
const isMinDate = (value: string) => {
  const selectedDate = new Date(value);
  return (
    (selectedDate && selectedDate >= new Date()) ||
    t("adDateSelector.ruleMsg.pastDate")
  );
};

/**
 * Date must be in at least today or in the future
 * @param value selected date
 */
const isMaxDate = (value: string) => {
  const selectedDate = new Date(value);
  return (
    (selectedDate && selectedDate <= maxAdvanceDate.value) ||
    t("adDateSelector.ruleMsg.maxDate")
  );
};
</script>

<style scoped></style>
