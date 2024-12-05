<template>
  <v-card
    flat
    class="mb-4"
    :title="data?.email"
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

import { Levels } from "@/api/error";
import { getUser } from "@/api/user-client";
import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import { useFindUser, useUserInfo } from "@/composables/api/useUserApi";
import { useSnackbar } from "@/composables/useSnackbar";
import { API_ERROR_MSG } from "@/Constants";
import { useUserStore } from "@/stores/user";
import User, { UserLocalDevelopment } from "@/types/User";

const userStore = useUserStore();

const snackbar = useSnackbar();

const {
  call: userInfoCall,
  data: userInfoData,
  error: userInfoError,
} = useUserInfo();

onMounted(async () => {
  // loadUser();
  await userInfoCall();

  // error catching
  if (userInfoError.value) {
    snackbar.sendMessage({
      level: Levels.ERROR,
      message: API_ERROR_MSG,
    });
  }
});

/**
 * Loads UserInfo from the backend and sets it in the store.
 */
function loadUserOld(): void {
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
