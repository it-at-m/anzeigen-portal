<template>
  <v-expansion-panels>
    <ad-card
      v-for="i in 7"
      :key="i"
      class="mb-4"
      @click="routeTo(i)"
    />
  </v-expansion-panels>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";

import AdCard from "@/components/Ad/AdCard.vue";

const router = useRouter();

const routeTo = (id: number) => {
  router.push({ path: "/ad", query: { id: id.toString() } });
};
</script>

<style scoped></style>
