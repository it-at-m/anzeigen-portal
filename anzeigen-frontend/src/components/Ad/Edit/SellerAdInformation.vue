<template>
  <ad-date-selector
    v-model="adTO.expiryDate"
    :disabled="disabled"
  />
  <v-text-field
    ref="refPhoneNumber"
    v-model="adTO.phone"
    color="accent"
    prepend-icon="mdi-phone-outline"
    class="w-md-66 w-sm-75"
    label="Telefonnummer"
    :rules="[minOneContactRule, rulePhoneNumber]"
    :disabled="disabled"
  />
  <v-text-field
    ref="refEmail"
    v-model="adTO.email"
    color="accent"
    prepend-icon="mdi-email-outline"
    class="w-md-66 w-sm-75"
    label="E-Mail Adresse"
    type="email"
    :rules="[minOneContactRule, ruleEmail]"
    :disabled="disabled"
  />
  <ad-agb-accept :disabled="disabled" />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useTemplateRef, watch } from "vue";

import AdAgbAccept from "@/components/Ad/Edit/AdAgbAccept.vue";
import AdDateSelector from "@/components/Ad/Edit/AdDateSelector.vue";
import { EMPTY_ADTO_OBJECT } from "@/Constants";

const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

const phoneNumber = defineModel<string>("phone", { default: "" });

const email = defineModel<string>("email", { default: "" });

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
  "Die E-Mail Adresse ist nicht valide";

/**
 * Validation rule for phone number - includes the '+' at the start
 * @param value the current input
 */
const rulePhoneNumber = (value: string) =>
  !value || /^\+?\d* ?\/?\d*$/.test(value) || "Die Telefonnummer ist ungültig";

/**
 * Minimum one contact needs to be set - this can be email or phone number
 */
const minOneContactRule = () =>
  !!adTO.value.email ||
  !!adTO.value.phone ||
  "Bitte geben Sie eine Telefonnummer und/oder (private) E-Mail-Adresse an.";
</script>
