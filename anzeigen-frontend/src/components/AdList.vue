<template>
  <ad-card
    v-for="ad in listOfAds"
    :key="ad.id"
    :ad-to="ad"
    class="mb-2"
  />
  <v-skeleton-loader
    v-if="loading"
    class="mb-2"
    type="heading, sentences, list-item-avatar"
  />
  <div class="d-flex justify-center">
    <v-btn
      v-if="!ads?.last && !loading"
      color="accent"
      @click="getMoreAds"
    >
      Mehr Ergebnisse laden
    </v-btn>
  </div>
</template>

<script setup lang="ts">
import type { AdTO } from "@/api/swbrett";
import type { DeepReadonly } from "vue";

import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

import AdCard from "@/components/Ad/AdCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";
import { useUpdateAdListEventBus } from "@/composables/useEventBus";

const useUpdateAdList = useUpdateAdListEventBus();

const route = useRoute();

const { data: ads, call: getAds, loading } = useGetAds();

const listOfAds = ref<DeepReadonly<AdTO[]>>([]);

const currentPageNumber = ref<number>(0);

useUpdateAdList.on(async () => {
  // EventBus is too quick - nextTick is too slow
  await new Promise((r) => setTimeout(r, 10));

  listOfAds.value = [];
  await getAdPage();
});

onMounted(async () => {
  // nextTick is too slow - slowed done
  await new Promise((r) => setTimeout(r, 500));
  await getAdPage();
});

const getMoreAds = () => {
  currentPageNumber.value++;
  getAdPage(currentPageNumber.value);
};

/**
 * Fetch a page of ads. This can be a new list of ads or a next page
 * @param page page number to get
 */
const getAdPage = async (page = 0) => {
  console.log(route.query);

  await getAds({
    isActive: true,
    page,
    ...route.query,
  });

  // append to list if next page was requested
  listOfAds.value =
    page > 0
      ? [...listOfAds.value, ...(ads.value?.content || [])]
      : ads.value?.content || [];
};
</script>

<style scoped></style>
