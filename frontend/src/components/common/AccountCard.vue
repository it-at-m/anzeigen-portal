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
import { useRouter } from "vue-router";

import Ad2ImageAvatar from "@/components/common/Ad2ImageAvatar.vue";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { ROUTES_MYBOARD } from "@/Constants";
import { useUserStore } from "@/stores/user";

const router = useRouter();

const userStore = useUserStore();

const defaultQuery = useDefaultQuery();

const { loading = false } = defineProps<{
  loading?: boolean;
}>();

/**
 * Provides routing functionality to navigate to a specific route.
 */
const routeTo = () => {
  router.push({
    name: ROUTES_MYBOARD,
    query: defaultQuery.value,
  });
};
</script>

<style scoped></style>
