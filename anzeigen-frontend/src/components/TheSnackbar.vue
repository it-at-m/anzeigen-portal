<template>
  <v-snackbar
    id="snackbar"
    v-model="show"
    variant="elevated"
    :color="color"
    :timeout="timeout"
    class="pa-0"
  >
    <v-layout class="align-center">
      <v-icon
        class="mr-3"
        :icon="computedIcon"
      />
      <div>{{ message }}</div>
    </v-layout>

    <template #actions>
      <v-btn
        v-if="isError"
        variant="text"
        @click="hide"
      >
        Schließen
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue";

import { Levels } from "@/api/error";
import { useSnackbarStore } from "@/stores/snackbar";

const snackbarStore = useSnackbarStore();

const defaultTimeout = 5000;

const show = ref(false);
const timeout = ref(defaultTimeout);
const message = ref("");
const color = ref("info");

const isError = computed(() => color.value === "error");

const computedIcon = computed(() => {
  switch (color.value) {
    case Levels.INFO:
      return "mdi-information-outline";
    case Levels.ERROR:
      return "mdi-alert-outline";
    case Levels.SUCCESS:
      return "mdi-check-circle-outline";
    case Levels.WARNING:
      return "mdi-alert-outline";
  }
});

watch(
  () => snackbarStore.message,
  () => (message.value = snackbarStore.message ?? "")
);

watch(
  () => snackbarStore.level,
  () => {
    color.value = snackbarStore.level;
    if (color.value === "error") {
      timeout.value = 0;
    } else {
      timeout.value = defaultTimeout;
    }
  }
);

watch(
  () => snackbarStore.show,
  () => {
    if (snackbarStore.show) {
      show.value = false;
      setTimeout(() => {
        show.value = true;
        snackbarStore.show = false;
      }, 100);
    }
  }
);

function hide(): void {
  show.value = false;
}
</script>

<style scoped>
.snackbar-content-align {
  display: flex;
  align-items: center;
  justify-items: center;
}
</style>
