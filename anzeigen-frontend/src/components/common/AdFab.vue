<template>
  <div>
    <v-fab
      v-if="isMobile"
      extended
      elevation="0"
      icon="mdi-plus"
      size="80"
      color="accent"
      app
      location="bottom"
    />
    <v-fab
      v-else
      extended
      elevation="0"
      prepend-icon="mdi-plus"
      text="Anzeige erstellen"
      color="accent"
      app
      location="bottom"
    />
  </div>
</template>

<script setup lang="ts">
import { breakpointsVuetifyV3, useBreakpoints } from "@vueuse/core/index";

const breakpoints = useBreakpoints(breakpointsVuetifyV3);
const isMobile = breakpoints.smallerOrEqual("md");
</script>

<style scoped></style>
