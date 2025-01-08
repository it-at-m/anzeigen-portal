<template>
  <ad-card
    v-for="ad in adStore.listOfAds"
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
import type { GetAdsRequest } from "@/api/swbrett";

import { computed, onMounted, watch } from "vue";
import { useRoute } from "vue-router";

import AdCard from "@/components/Ad/AdCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";
import { ROUTES_MYBOARD } from "@/Constants";
import { useAdStore } from "@/stores/adStore";
import { useUserStore } from "@/stores/user";

const route = useRoute();

const { data: ads, call: getAds, loading } = useGetAds();

const adStore = useAdStore();

const userStore = useUserStore();

/**
 * Initializes the store with ads
 */
onMounted(async () => {
  if (adStore.isEmpty) {
    await getAdPage(false);
  }
});

const updateUrl = computed(() => [route.name, route.path]);

const routeName = computed(() => route.name);

/**
 * Triggers refresh of ad list after changes in url
 */
watch(updateUrl, async () => {
  await getAdPage(false);
});

/**
 * Get the next page via getAdPage
 */
const getMoreAds = () => {
  getAdPage(true);
};

/**
 * Fetch a page of ads. This can be a new list of ads or a next page
 * @param isNextPage
 */
const getAdPage = async (isNextPage: boolean) => {
  const requestQuery: GetAdsRequest = {
    isActive: true,
    page: isNextPage ? adStore.nextPage : 0,
    ...route.query,
  };

  if (routeName.value === ROUTES_MYBOARD) {
    requestQuery.userId = userStore.userID?.toString();
  }

  await getAds(requestQuery);

  if (ads.value) {
    adStore.addNewAds(ads.value, isNextPage);
  }
};
</script>

<style scoped></style>
