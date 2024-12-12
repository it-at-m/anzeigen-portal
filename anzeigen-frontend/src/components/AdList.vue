<template>
  <v-btn
    color="accent"
    @click="loadAds"
  >
    Refresh List
  </v-btn>
  <div>
    <p>{{ ads?.content?.length || -1 }}</p>
  </div>
  <ad-card
    v-for="i in 7"
    :key="i"
    class="mb-4"
    @click="routeTo(i)"
  />
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";

import AdCard from "@/components/Ad/AdCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";

const { data: ads, call: getAds, loading } = useGetAds();

const loadAds = () => {
  getAds({
    isActive: true,
    type: "SEEK",
  });
};

const router = useRouter();

const routeTo = (id: number) => {
  router.push({ path: "/ad", query: { id: id.toString() } });
};
</script>

<style scoped></style>
