<template>
  <v-date-input
    v-model="expiryDate"
    class="w-md-66 w-sm-75"
    label="Ablaufdatum"
    :min="yesterdayDate"
  />
  <v-text-field
    ref="refPhoneNumber"
    v-model="phoneNumber"
    prepend-icon="mdi-phone-outline"
    class="w-md-66 w-sm-75"
    label="Telefonnummer"
    :rules="[minOneContactRule, rulePhoneNumber]"
  />
  <v-text-field
    ref="refEmail"
    v-model="email"
    prepend-icon="mdi-email-outline"
    class="w-md-66 w-sm-75"
    label="E-Mail Adresse"
    type="email"
    :rules="[minOneContactRule, ruleEmail]"
  />
  <ad-agb-accept />
</template>

<script setup lang="ts">
import { computed, useTemplateRef, watch } from "vue";
import { VDateInput } from "vuetify/labs/components";

import AdAgbAccept from "@/components/Ad/Edit/AdAgbAccept.vue";

const expiryDate = defineModel<Date>("expiryDate");

const phoneNumber = defineModel<string>("phone", { default: "" });

const email = defineModel<string>("email", { default: "" });

const refPhone = useTemplateRef("refPhoneNumber");
const refEmail = useTemplateRef("refEmail");

/**
 * Watches the input for phone number / email and triggers the validation again.
 * This needs to be done, otherwise one input can retain its error state
 */
watch([phoneNumber, email], () => {
  refPhone.value?.validate();
  refEmail.value?.validate();
});

/**
 * Returns date object for yesterday (today - 1)
 */
const yesterdayDate = computed(() => {
  const todayDate = new Date();
  todayDate.setDate(todayDate.getDate() - 1);
  return todayDate;
});

/**
 * Validation rule for email - do not ask me why it is so complicated
 * @param value the current input
 */
const ruleEmail = (value: string) =>
  /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/.test(
    value
  ) || "Die E-Mail Adresse ist nicht valide";

/**
 * Validation rule for phone number - includes the '+' at the start
 * @param value the current input
 */
const rulePhoneNumber = (value: string) =>
  /^\+?\d*$/.test(value) || "Die Telefonnummer ist ungültig";

/**
 * Minimum one contact needs to be set - this can be email or phone number
 */
const minOneContactRule = () =>
  !!email.value ||
  !!phoneNumber.value ||
  "Bitte geben Sie eine Telefonnummer und/oder (private) E-Mail-Adresse an.";
</script>
