<template>
  <v-snackbar-queue v-model="queue">
    <template #text="textData">
      <v-layout class="align-center">
        <v-icon
          class="mr-3"
          size="25"
          :icon="textData.item.icon"
        />
        <p class="text-body-1">{{ textData.item.text }}</p>
      </v-layout>
    </template>
  </v-snackbar-queue>
</template>

<script setup lang="ts">
import type { SnackbarMessage } from "@/types/SnackbarMessage";

import { ref } from "vue";
import { VSnackbarQueue } from "vuetify/labs/VSnackbarQueue";

import { Levels } from "@/api/error";
import { snackbarBus } from "@/composables/useSnackbar";

/**
 * Internal state of all messages to be displayed.
 */
const queue = ref<SnackbarMessageInternal[]>([]);

interface SnackbarMessageInternal {
  text: string;
  timeout: number;
  color: string;
  icon: string;
}

/**
 * Pushes a new message onto the state.
 */
snackbarBus.on((message: SnackbarMessage) => {
  queue.value.push({
    color: message.level || "info",
    text: message.message || "",
    timeout: message.timeout || message.level === Levels.ERROR ? 5000 : 2500,
    icon: getIcon(message.level || Levels.INFO),
  });
});

/**
 * Computes the icon according to the message level.
 * @param level
 */
const getIcon = (level: Levels) => {
  switch (level) {
    case Levels.INFO:
      return "mdi-information-outline";
    case Levels.ERROR:
      return "mdi-alert-outline";
    case Levels.SUCCESS:
      return "mdi-check-circle-outline";
    case Levels.WARNING:
      return "mdi-alert-outline";
  }
};
</script>

<style scoped></style>
