<template>
  <v-checkbox
    v-model="isChecked"
    color="accent"
    :disabled="disabled"
  >
    <template #label>
      <p>
        Ich bin mit den
        <a :href="agbFile">Nutzungsbedingungen (AGB)</a> einverstanden. Das
        Schwarze Brett darf nur zu rein privaten Zwecken genutzt werden.
        Insbesondere Werbung kommerzieller Art ist nicht gestattet!
        Informationen zur Verarbeitung personenbezogener Daten enthalten die
        <a :href="securityFile">Datenschutzhinweise</a>.
      </p>
    </template>
  </v-checkbox>
</template>

<script setup lang="ts">
import { useObjectUrl } from "@vueuse/core";
import { onMounted, ref } from "vue";

import AGB from "@/static/AGB-1.pdf";
import SecInfo from "@/static/Datenschutzhinweise.pdf";

/**
 * Value if checkbox is checked
 */
const isChecked = defineModel<boolean>({ default: false });

/**
 * Disables the checkbox input
 */
defineProps<{
  disabled?: boolean;
}>();

/**
 * Blob and objectUrl for AGB
 */
const agbBlob = ref();
const agbFile = useObjectUrl(agbBlob);

/**
 * Blob and objectUrl for Datenschutzhinweise
 */
const securityBlob = ref();
const securityFile = useObjectUrl(securityBlob);

/**
 * Load files on mount
 */
onMounted(async () => {
  const [agbResponse, secResponse] = await Promise.all([
    fetch(AGB),
    fetch(SecInfo),
  ]);

  agbBlob.value = await agbResponse.blob();
  securityBlob.value = await secResponse.blob();
});
</script>
