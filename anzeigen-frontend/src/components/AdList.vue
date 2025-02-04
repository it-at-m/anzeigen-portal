<template>
  <div v-if="!loading">
    <ad-card
      v-for="ad in adStore.listOfAds"
      :key="ad.id"
      :ad-to="ad"
      class="mb-4"
    />
  </div>
  <ad-display-card v-if="adStore.isEmpty && !loading">
    <template #title> Es konnte keine Anzeige gefunden werden </template>
    <template #subtitle>
      Ändern Sie die Kategorie, Art oder den Suchbegriff um andere Anzeigen zu
      finden.
    </template>
    <template #text>
      <router-link
        :to="{
          name: ROUTES_BOARD,
          query: defaultQuery,
        }"
      >
        Klicken Sie hier um alle verfügbaren Anzeigen zu sehen.
      </router-link>
    </template>
  </ad-display-card>
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

import AdCard from "@/components/Ad/list/AdCard.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { useUpdateAdListEventBus } from "@/composables/useEventBus";
import { ROUTES_BOARD, ROUTES_MYBOARD } from "@/Constants";
import { useAdStore } from "@/stores/adStore";
import { useUserStore } from "@/stores/user";

const route = useRoute();
const defaultQuery = useDefaultQuery();

const { data: ads, call: getAds, loading } = useGetAds();

const adStore = useAdStore();

const userStore = useUserStore();

const updateAdListEventBus = useUpdateAdListEventBus();

/**
 * Initializes the store with ads
 */
onMounted(async () => {
  if (adStore.isEmpty) {
    await getAdPage(false);
  }
});

/**
 * Triggers refresh upon event - used by dialog
 */
updateAdListEventBus.on(async () => {
  await getAdPage(false);
});

/**
 * Computed property for easier watching of route.name and path
 */
const updateUrl = computed(() => [route.name, route.path]);

/**
 * Reactive route.name
 */
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
