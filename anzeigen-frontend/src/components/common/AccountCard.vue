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
import { onMounted } from "vue";

import { getUser } from "@/api/user-client";
import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import { useUserStore } from "@/stores/user";
import User, { UserLocalDevelopment } from "@/types/User";

const userStore = useUserStore();

onMounted(() => {
  loadUser();
});

/**
 * Loads UserInfo from the backend and sets it in the store.
 */
function loadUser(): void {
  getUser()
    .then((user: User) => userStore.setUser(user))
    .catch(() => {
      // No user info received, so fallback
      if (import.meta.env.DEV) {
        userStore.setUser(UserLocalDevelopment());
      } else {
        userStore.setUser(null);
      }
    });
}
</script>

<style scoped></style>
