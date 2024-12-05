<template>
  <v-card
    flat
    class="mb-4"
    :title="userStore.getUser?.displayName"
    :loading="loading"
    :disabled="loading"
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

const { loading = false } = defineProps<{
  loading?: boolean;
}>();
</script>

<style scoped></style>
