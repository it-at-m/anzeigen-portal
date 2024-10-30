<template>
  <v-card
    flat
    class="mb-4"
    title="Vorname Nachname"
    subtitle="Hier gehts zu Ihren Anzeigen"
    link
  >
    <template #prepend>
      <ad2-image-avatar username="vorname nachname" />
    </template>
  </v-card>
</template>

<script setup lang="ts">
import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
</script>

<style scoped></style>
