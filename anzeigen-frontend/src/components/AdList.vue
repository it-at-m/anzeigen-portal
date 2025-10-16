<template>
  <div>
    <ad-card
      v-for="ad in adStore.listOfAds"
      :key="ad.id"
      :ad-to="ad"
      class="mb-4"
    />
  </div>
  <ad-display-card
    v-if="adStore.isEmpty && !loading"
    class="mb-4"
  >
    <template #title> {{ t("adList.noAdFound.title") }} </template>
    <template #subtitle>
      {{ t("adList.noAdFound.subTitle") }}
    </template>
    <template #text>
      <router-link
        :to="{
          name: ROUTES_BOARD,
          query: defaultQuery,
        }"
      >
        {{ t("adList.noAdFound.text") }}
      </router-link>
    </template>
  </ad-display-card>
  <v-skeleton-loader
    v-if="loading"
    type="heading, sentences, list-item-avatar"
  />
  <div class="d-flex justify-center mt-2">
    <v-btn
      v-if="!adStore.meta?.last && !loading"
      color="accent"
      @click="getMoreAds"
    >
      {{ t("adList.loadMore") }}
    </v-btn>
  </div>
</template>

<script setup lang="ts">
import type { GetAdsRequest } from "@/api/swbrett";

import { computed, onMounted, watch } from "vue";
import { useI18n } from "vue-i18n";
import { useRoute } from "vue-router";

import AdCard from "@/components/Ad/list/AdCard.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { useUpdateAdListEventBus } from "@/composables/useEventBus";
import { ROUTES_BOARD, ROUTES_MYBOARD } from "@/Constants";
import { useAdStore } from "@/stores/adStore";
import { useUserStore } from "@/stores/user";

const { t } = useI18n();

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
  if (adStore.isEmpty && Object.keys(route.query).length != 0) {
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

  // Not proud of this conversion - workaround for https://github.com/it-at-m/refarch/blob/00be5e8a4462ed93f8d32a6593a0d162d24da865/refarch-gateway/src/main/java/de/muenchen/refarch/gateway/filter/GlobalRequestParameterPollutionFilter.java
  if (requestQuery.type) {
    requestQuery.type = requestQuery.type.toString();
  }

  if (!userStore.isAdmin) {
    requestQuery.isActive = true;
  }

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
