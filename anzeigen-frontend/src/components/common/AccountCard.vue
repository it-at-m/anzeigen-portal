<template>
  <v-card
    flat
    class="mb-4"
    :title="userStore.getUser?.displayName"
    :loading="loading"
    :disabled="loading"
    subtitle="Hier geht es zu Ihren Anzeigen"
    @click="routeTo"
  >
    <template #prepend>
      <ad2-image-avatar username="vorname nachname" />
    </template>
  </v-card>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";

import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import { useUserStore } from "@/stores/user";

const router = useRouter();

const route = useRoute();

const userStore = useUserStore();

const { loading = false } = defineProps<{
  loading?: boolean;
}>();

const routeTo = () => {
  router.push({
    path: "/myBoard",
    query: route.query,
  });
};
</script>

<style scoped></style>
