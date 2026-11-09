<template>
  <ad-display-card>
    <template #title> Weitere Anzeigen dieses Nutzers </template>
    <template #text>
      <v-btn
        variant="tonal"
        color="accent"
        prepend-icon="mdi-window-close"
        @click="emit('click')"
      >
        Auswahl zurücksetzen
      </v-btn>
    </template>
  </ad-display-card>
</template>

<script setup lang="ts">
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";

const emit = defineEmits<{
  click: [];
}>();
</script>

<style scoped></style>
