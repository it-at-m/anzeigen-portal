<template>
  <v-text-field
    v-model="adTO.link"
    prepend-icon="mdi-link"
    label="Link (optional)"
    class="w-md-66 w-sm-75"
    type="url"
    :disabled="disabled"
    :rules="[() => true, ruleLink]"
  />
  <ad-titel-picture
    v-model="adTO.adImg"
    style="margin-bottom: 16px !important"
  />
  <ad-files-upload v-model="adTO.adFiles" />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import AdFilesUpload from "@/components/Ad/Edit/AdFilesUpload.vue";
import AdTitelPicture from "@/components/Ad/Edit/AdTitelPicture.vue";
import { EMPTY_ADTO_OBJECT } from "@/Constants";

const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

defineProps<{
  disabled?: boolean;
}>();

const ruleLink = (value: string) =>
  !value ||
  /^https:\/\/(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$/.test(
    value
  ) ||
  "Bitte geben Sie einen validen Link an.";
</script>

<style scoped></style>
