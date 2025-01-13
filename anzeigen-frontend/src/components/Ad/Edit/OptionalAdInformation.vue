<template>
  <v-file-input
    v-model="pictureInternal"
    prepend-icon="mdi-camera"
    label="Foto auswählen"
    class="w-md-66 w-sm-75"
    :disabled="disabled"
  />
  <ad-files-upload v-model="adTO.adFiles" />
  <v-file-input
    v-model="additionalFileInternal"
    label="Optionale Anhänge"
    class="w-md-66 w-sm-75"
    multiple
    show-size
    :disabled="disabled"
  />
  <v-text-field
    v-model="link"
    prepend-icon="mdi-link"
    label="Link (optional)"
    class="w-md-66 w-sm-75"
    type="url"
    :disabled="disabled"
    :rules="[ruleLink]"
  />
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";

import { ref } from "vue";
import { VFileUpload } from "vuetify/labs/VFileUpload";

import AdFilesUpload from "@/components/Ad/Edit/AdFilesUpload.vue";
import { EMPTY_ADTO_OBJECT } from "@/Constants";

const adTO = defineModel<AdTO>({ default: EMPTY_ADTO_OBJECT });

defineProps<{
  disabled?: boolean;
}>();

const link = defineModel<string>("link");

const pictureInternal = ref<File>();

const additionalFileInternal = ref<File[]>();

const ruleLink = (value: string) =>
  /^https:\/\/(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$/.test(
    value
  ) || "Bitte geben Sie einen validen Link an.";
</script>

<style scoped></style>
