<template>
  <v-btn
    color="accent"
    @click="getAdPage"
  >
    Refresh List
  </v-btn>
  <div>
    <p>{{ listOfAds.length }}</p>
    <p>{{ myBoard }}</p>
  </div>
  <v-progress-linear
    v-show="loading"
    indeterminate
  />
  <ad-card
    v-for="i in 7"
    :key="i"
    class="mb-4"
    @click="routeTo(i)"
  />
  <v-btn
    v-if="!ads?.last"
    color="accent"
    @click="getMoreAds"
  >
    Mehr Ergebnisse laden
  </v-btn>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";
import type { DeepReadonly } from "vue";

import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

import AdCard from "@/components/Ad/AdCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";
import { useUpdateAdListEventBus } from "@/composables/useEventBus";
import { useMyBoard } from "@/composables/useMyBoard";

const useUpdateAdList = useUpdateAdListEventBus();

const router = useRouter();

const route = useRoute();

const myBoard = useMyBoard();

const { data: ads, call: getAds, loading } = useGetAds();

const listOfAds = ref<DeepReadonly<AdTO[]>>([]);

const currentPageNumber = ref<number>(0);

useUpdateAdList.on(() => {
  // call getAds whenether something changes
  listOfAds.value = [];
  getAdPage();
});

onMounted(() => getAdPage());

const getMoreAds = () => {
  currentPageNumber.value++;
  getAdPage(currentPageNumber.value);
};

/**
 * Fetch a page of ads. This can be a new list of ads or a next page
 * @param page page number to get
 */
const getAdPage = async (page = 0) => {
  await getAds({
    isActive: true,
    page,
    ...route.query,
  });

  // append to list if next page was requested
  listOfAds.value =
    page > 1
      ? [...listOfAds.value, ...(ads.value?.content || [])]
      : ads.value?.content || [];
};

/**
 * Route to a specific ad
 * @param id of the ad
 */
const routeTo = (id: number) => {
  router.push({ path: "/ad", query: { id: id.toString() } });
};
</script>

<style scoped></style>
