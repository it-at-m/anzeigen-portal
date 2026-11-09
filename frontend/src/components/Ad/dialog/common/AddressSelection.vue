<template>
  <v-text-field
    :model-value="modelValue?.street"
    variant="outlined"
    density="compact"
    color="accent"
    :disabled="disabled"
    class="my-4"
    hide-details="auto"
    :label="t(`addressSelection.street`)"
    :rules="[addressFieldNotEmptyRule]"
    @update:model-value="updatedStreet"
  />
  <v-text-field
    :model-value="modelValue?.postalCode"
    variant="outlined"
    density="compact"
    color="accent"
    :disabled="disabled"
    class="my-4"
    hide-details="auto"
    :label="t(`addressSelection.postalCode`)"
    :rules="[addressFieldNotEmptyRule]"
    @update:model-value="updatedPostalCode"
  />
</template>

<script setup lang="ts">
import type { Address } from "@/api/swbrett";

import { useI18n } from "vue-i18n";

import { CONFIG } from "@/Constants.ts";

const { t } = useI18n();

const { modelValue } = defineProps<{
  disabled?: boolean;
  modelValue: Address | undefined;
}>();

const emit = defineEmits<{
  "update:modelValue": [modelValue: Address | undefined];
}>();

const addressFieldNotEmptyRule = (value: string) =>
  !CONFIG.IS_ADDRESS_MANDATORY ||
  !!value ||
  t("sellerAdInformation.ruleMsg.addressFields");

const updatedStreet = (value: string) => {
  emit("update:modelValue", { ...modelValue, street: value });
};
const updatedPostalCode = (value: string) => {
  emit("update:modelValue", { ...modelValue, postalCode: value });
};
</script>

<style scoped></style>
