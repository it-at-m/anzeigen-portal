<template>
  <a
    href="https://wilma.muenchen.de/pages/gesamtpersonalrat"
    target="_blank"
  >
    <v-toolbar-title class="d-flex justify-end align-center">
      <v-avatar
        size="36px"
        :image="emblem"
      />
    </v-toolbar-title>
  </a>
</template>

<script setup lang="ts">
import emblem from "@/assets/GPR-Logo.jpg";
</script>
