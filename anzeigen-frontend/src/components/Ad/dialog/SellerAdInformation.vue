<template>
  <ad-date-selector
    v-model="adTO.expiryDate"
    max-date-setting="MAX_EXPIRY_DATE_RANGE"
    :disabled="disabled"
    class="mb-4"
  />
  <v-text-field
    ref="refPhoneNumber"
    v-model="adTO.phone"
    density="compact"
    variant="outlined"
    color="accent"
    prepend-icon="mdi-phone-outline"
    :label="t('sellerAdInformation.labelPhone')"
    hide-details="auto"
    :rules="[minOneContactRule, rulePhoneNumber]"
    :disabled="disabled"
    class="mb-4"
  />
  <v-text-field
    ref="refEmail"
    v-model="adTO.email"
    variant="outlined"
    density="compact"
    color="accent"
    prepend-icon="mdi-email-outline"
    :label="t('sellerAdInformation.labelEmail')"
    type="email"
    hide-details="auto"
    :rules="[minOneContactRule, ruleEmail]"
    :disabled="disabled"
    class="mb-4"
  />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";

import AdDateSelector from "@/components/Ad/dialog/seller/AdDateSelector.vue";
import { EMPTY_ADTO_OBJECT } from "@/Constants";

const { t } = useI18n();

const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

defineProps<{
  disabled?: boolean;
}>();

const refPhone = useTemplateRef("refPhoneNumber");
const refEmail = useTemplateRef("refEmail");

/**
 * Watches the input for phone number / email and triggers the validation again.
 * This needs to be done, otherwise one input can retain its error state
 */
watch(
  () => [adTO.value.phone, adTO.value.email],
  () => {
    refPhone.value?.validate();
    refEmail.value?.validate();
  }
);

/**
 * Validation rule for email - do not ask me why it is so complicated
 * @param value the current input
 */
const ruleEmail = (value: string) =>
  !value ||
  /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/.test(
    value
  ) ||
  t("sellerAdInformation.invalidEmail");

/**
 * Validation rule for phone number - includes the '+' at the start
 * @param value the current input
 */
const rulePhoneNumber = (value: string) =>
  !value ||
  /^\+?\d* ?\/?\d*$/.test(value) ||
  t("sellerAdInformation.invalidPhoneNumber");

/**
 * Minimum one contact needs to be set - this can be email or phone number
 */
const minOneContactRule = () =>
  !!adTO.value.email ||
  !!adTO.value.phone ||
  t("sellerAdInformation.dutyField");
</script>
