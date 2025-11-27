<template>
  <a
    href="https://www.muenchen.de/"
    target="_blank"
  >
    <v-toolbar-title class="font-weight-bold d-flex justify-end">
      <v-avatar
        size="36px"
        :image="emblem"
      />
    </v-toolbar-title>
  </a>
</template>

<script setup lang="ts">
import emblem from "@/assets/muc-kindl.jpg";
</script>

<style scoped></style>
