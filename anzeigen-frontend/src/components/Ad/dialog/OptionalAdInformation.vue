<template>
  <ad-titel-picture
    v-model="adTO.adImg"
    :disabled="disabled"
    style="margin-bottom: 16px !important"
  />
  <ad-files-upload
    v-model="adTO.adFiles"
    :disabled="disabled"
  />
  <v-text-field
    v-model="adTO.link"
    variant="outlined"
    density="compact"
    prepend-icon="mdi-link"
    :label="t('optionalAdInformation.label')"
    hide-details="auto"
    color="accent"
    type="url"
    :disabled="disabled"
    :rules="[() => true, ruleLink]"
  />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { useI18n } from "vue-i18n";

import AdFilesUpload from "@/components/Ad/dialog/optional/AdFilesUpload.vue";
import AdTitelPicture from "@/components/Ad/dialog/optional/AdTitelPicture.vue";
import { EMPTY_ADTO_OBJECT } from "@/Constants";

const { t } = useI18n();

const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

defineProps<{
  disabled?: boolean;
}>();

/**
 * Validates if the given string is a valid URL.
 *
 * @param value - The URL string to be validated.
 * @returns An error message if the URL is invalid, otherwise returns true.
 */
const ruleLink = (value: string) =>
  !value ||
  /^https:\/\/(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$/.test(
    value
  ) ||
  t("optionalAdInformation.ruleMsg");
</script>

<style scoped></style>
