<template>
  <v-avatar size="48">
    <img
      :src="avatarUrl"
      :alt="altText"
    />
  </v-avatar>
</template>
<script setup lang="ts">
import { computed } from "vue";

import { DefaultLhmAvatarService } from "@/api/ad2image-avatar-client.ts";

const {
  username,
  avatarMode = "fallbackGeneric",
  avatarSize = "64",
} = defineProps<{
  username: string;
  avatarMode?: string;
  avatarSize?: string;
}>();

/**
 * Computes the URL for the user's avatar.
 */
const avatarUrl = computed(() => {
  return DefaultLhmAvatarService.avatarHref(username, avatarMode, avatarSize);
});

/**
 * Computes the alt text for the user's avatar.
 */
const altText = computed(() => `Bild von ${username}`);
</script>
