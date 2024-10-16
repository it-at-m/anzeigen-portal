<template>
  <div>
    <v-fab
      v-if="isMobile"
      extended
      variant="flat"
      icon="mdi-plus"
      size="64"
      color="accent"
      app
      location="bottom"
    />
    <v-fab
      v-else
      extended
      variant="flat"
      prepend-icon="mdi-plus"
      text="Anzeige erstellen"
      color="accent"
      app
      location="bottom"
    />
  </div>
</template>

<script setup lang="ts">
import { breakpointsVuetifyV3, useBreakpoints } from "@vueuse/core";

const breakpoints = useBreakpoints(breakpointsVuetifyV3);
const isMobile = breakpoints.smallerOrEqual("md");
</script>

<style scoped></style>
