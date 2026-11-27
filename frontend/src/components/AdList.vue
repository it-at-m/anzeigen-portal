<template>
  <v-infinite-scroll
    v-if="!adStore.isEmpty"
    ref="infiniteScroll"
    :class="{ 'board-sidebar-sticky': mdAndUp }"
    height="calc(100dvh - 100px)"
    @load="onLoad"
  >
    <ad-card
      v-for="ad in adStore.listOfAds"
      :key="ad.id"
      :ad-to="ad"
      class="mb-4"
    />

    <template #loading>
      <v-skeleton-loader
        class="w-100"
        type="heading, sentences, list-item-avatar"
      />
    </template>

    <template #empty>
      <div class="text-body-medium text-medium-emphasis py-2">
        {{ t("adList.noMoreAds") }}
      </div>
    </template>
  </v-infinite-scroll>

  <ad-display-card
    v-else-if="!loading"
    class="mb-4"
  >
    <template #title>{{ t("adList.noAdFound.title") }}</template>
    <template #subtitle>{{ t("adList.noAdFound.subTitle") }}</template>
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
    v-else
    type="heading, sentences, list-item-avatar"
  />
</template>

<script setup lang="ts">
import type { GetAdsRequest } from "@/apis/generated/anzeigen-backend";
import type { VInfiniteScroll } from "vuetify/components";

import { computed, onMounted, useTemplateRef, watch } from "vue";
import { useI18n } from "vue-i18n";
import { useRoute } from "vue-router";
import { useDisplay } from "vuetify/framework";

import AdCard from "@/components/Ad/list/AdCard.vue";
import AdDisplayCard from "@/components/common/AdDisplayCard.vue";
import { useGetAds } from "@/composables/api/useAdApi";
import { useDefaultQuery } from "@/composables/useDefaultQuery.ts";
import { useUpdateAdListEventBus } from "@/composables/useEventBus";
import useHasAnyRole from "@/composables/useHasAnyRole.ts";
import { ROUTES_BOARD, ROUTES_MYBOARD } from "@/constants.ts";
import { useAdStore } from "@/stores/adStore";
import { useUserInfoStore } from "@/stores/userinfo.ts";
import { Role } from "@/types/Role.ts";

const { t } = useI18n();

const route = useRoute();
const defaultQuery = useDefaultQuery();

const { data: ads, call: getAds, loading } = useGetAds();

const adStore = useAdStore();

const userStore = useUserInfoStore();
const hasAdminRole = useHasAnyRole(Role.FACHADMIN);
const { mdAndUp } = useDisplay();

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
const routePath = computed(() => route.path);

/**
 * Triggers refresh of ad list after changes in url
 */
watch(updateUrl, async () => {
  await getAdPage(false);
});

interface InfiniteScrollLoadEvent {
  done: (status: "ok" | "empty" | "error") => void;
}

const infiniteScroll = useTemplateRef<VInfiniteScroll>("infiniteScroll");

const onLoad = async ({ done }: InfiniteScrollLoadEvent) => {
  if (adStore.meta?.last) {
    done("empty");
    return;
  }

  try {
    await getAdPage(true);
    done(adStore.meta?.last ? "empty" : "ok");
  } catch {
    done("error");
  }
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

  // TODO - this can now be corrected as it can be whitelisted
  // Not proud of this conversion - workaround for https://github.com/it-at-m/refarch/blob/00be5e8a4462ed93f8d32a6593a0d162d24da865/refarch-gateway/src/main/java/de/muenchen/refarch/gateway/filter/GlobalRequestParameterPollutionFilter.java
  if (requestQuery.type) {
    requestQuery.type = requestQuery.type.toString();
  }

  if (!hasAdminRole) {
    requestQuery.isActive = true;
  }

  if (routePath.value === ROUTES_MYBOARD) {
    requestQuery.userId = userStore.userID?.toString();
  }

  await getAds(requestQuery);

  if (ads.value) {
    adStore.addNewAds(ads.value, isNextPage);
  }

  if (!isNextPage) {
    infiniteScroll.value?.reset("end");
  }
};
</script>

<style scoped>
.board-sidebar-sticky {
  --sidebar-gap: 16px;
  position: sticky;
  top: calc(var(--v-layout-top, 0px) + var(--sidebar-gap));
  max-height: calc(100dvh - var(--v-layout-top, 0px) - var(--sidebar-gap) * 2);
  overflow-y: auto;
  align-self: start;
}
</style>
