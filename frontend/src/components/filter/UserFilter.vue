<template>
  <ad-display-card>
    <template #title> Weitere Anzeigen dieses Nutzers </template>
    <template #text>
      <v-btn
        variant="tonal"
        color="accent"
        :prepend-icon="mdiWindowClose"
        @click="emit('click')"
      >
        Auswahl zurücksetzen
      </v-btn>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import { mdiWindowClose } from "@mdi/js";

import AdDisplayCard from "@/components/common/AdDisplayCard.vue";

const emit = defineEmits<{
  click: [];
}>();
</script>

<style scoped></style>
