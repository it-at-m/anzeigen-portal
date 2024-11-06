<template>
  <v-expansion-panels>
    <ad-card
      v-for="i in 7"
      :key="i"
      class="mb-4"
    />
  </v-expansion-panels>
</template>

<script setup lang="ts">
import AdCard from "@/components/Ad/AdCard.vue";
</script>

<style scoped></style>
